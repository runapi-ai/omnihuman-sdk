package ai.runapi.omnihuman;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.errors.ValidationException;
import ai.runapi.core.http.HttpRequest;
import ai.runapi.core.http.HttpResponse;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.http.JsonRequestBody;
import ai.runapi.core.json.Json;
import ai.runapi.omnihuman.types.CompletedAudioToVideoResponse;
import ai.runapi.omnihuman.types.AudioToVideoResponse;
import ai.runapi.omnihuman.types.AudioToVideoModel;
import ai.runapi.omnihuman.types.AudioToVideoParams;
import ai.runapi.omnihuman.types.AudioToVideoResponse;
import ai.runapi.omnihuman.types.CompletedAudioToVideoResponse;
import ai.runapi.omnihuman.types.CompletedHumanIdentificationResponse;
import ai.runapi.omnihuman.types.CompletedSubjectDetectionResponse;
import ai.runapi.omnihuman.types.HumanIdentificationModel;
import ai.runapi.omnihuman.types.HumanIdentificationParams;
import ai.runapi.omnihuman.types.HumanIdentificationResponse;
import ai.runapi.omnihuman.types.SubjectDetectionModel;
import ai.runapi.omnihuman.types.SubjectDetectionParams;
import ai.runapi.omnihuman.types.SubjectDetectionResponse;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class OmnihumanClientTest {
  @Test
  void builderCreatesClientAndUniversalResources() {
    OmnihumanClient client = OmnihumanClient.builder().apiKey("sk-test").build();

    assertNotNull(client.audioToVideo());
    assertNotNull(client.files());
    assertNotNull(client.account());
  }

  @Test
  void openValueClassesSerializeAsScalarStrings() throws Exception {
    String json = Json.mapper().writeValueAsString(new AudioToVideoModel("omnihuman-1.5"));

    assertEquals("\"omnihuman-1.5\"", json);
    assertEquals(new AudioToVideoModel("omnihuman-1.5"), Json.mapper().readValue(json, AudioToVideoModel.class));
  }

  @Test
  void createSendsExpectedRequestShape() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_123\",\"status\":\"processing\"}");
    OmnihumanClient client = OmnihumanClient.builder().apiKey("sk-test").transport(transport).build();

    client.audioToVideo().create(
        AudioToVideoParams.builder()
            .model(AudioToVideoModel.OMNIHUMAN_1_5)
            .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
            .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
            .prompt("A small red cube on a plain white table, studio product photo")
            .build()
    );

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/omnihuman/audio_to_video", transport.request.getPath());
    JsonNode body = bodyJson(transport.request);
    assertNotNull(body);
  }

  @Test
  void getDecodesTaskResponseAndExtraFields() {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_456\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}],\"custom\":\"kept\"}");
    OmnihumanClient client = OmnihumanClient.builder().apiKey("sk-test").transport(transport).build();

    AudioToVideoResponse response = client.audioToVideo().get("task_456");

    assertEquals("GET", transport.request.getMethod().name());
    assertEquals("/api/v1/omnihuman/audio_to_video/task_456", transport.request.getPath());
    assertEquals("completed", response.getStatus().value());
    assertNotNull(response.getVideos());
    assertEquals("kept", response.extraFields().get("custom").asText());
  }

  @Test
  void runPollsUntilCompletedAndKeepsExtraFields() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_789\",\"status\":\"processing\"}",
        "{\"id\":\"task_789\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}],\"custom\":\"kept\"}");
    OmnihumanClient client = OmnihumanClient.builder().apiKey("sk-test").transport(transport).build();

    CompletedAudioToVideoResponse response = client.audioToVideo().run(
        AudioToVideoParams.builder()
            .model(AudioToVideoModel.OMNIHUMAN_1_5)
            .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
            .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
            .prompt("A small red cube on a plain white table, studio product photo")
            .build(),
        RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());

    assertEquals("completed", response.getStatus().value());
    assertNotNull(response.getVideos());
    assertEquals("kept", response.extraFields().get("custom").asText());
    assertEquals(2, transport.calls);
  }

  @Test
  void runRejectsCompletedResponseMissingResultField() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_missing\",\"status\":\"processing\"}",
        "{\"id\":\"task_missing\",\"status\":\"completed\"}");
    OmnihumanClient client = OmnihumanClient.builder().apiKey("sk-test").transport(transport).build();

    assertThrows(
        ValidationException.class,
        () -> client.audioToVideo().run(
                AudioToVideoParams.builder()
                    .model(AudioToVideoModel.OMNIHUMAN_1_5)
                    .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                    .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                    .prompt("A small red cube on a plain white table, studio product photo")
                    .build(),
            RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
  }

    @Test
    void coversAudiotovideoResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_audio_to_video\",\"status\":\"processing\"}");
      OmnihumanClient createClient = OmnihumanClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.audioToVideo().create(
              AudioToVideoParams.builder()
                  .model(AudioToVideoModel.OMNIHUMAN_1_5)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_audio_to_video_options\",\"status\":\"processing\"}");
      OmnihumanClient createWithOptionsClient = OmnihumanClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.audioToVideo().create(
              AudioToVideoParams.builder()
                  .model(AudioToVideoModel.OMNIHUMAN_1_5)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_audio_to_video\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      OmnihumanClient getClient = OmnihumanClient.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.audioToVideo().get("task_audio_to_video"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_audio_to_video_options\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      OmnihumanClient getWithOptionsClient = OmnihumanClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.audioToVideo().get("task_audio_to_video_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_audio_to_video_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_audio_to_video_run\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      OmnihumanClient runClient = OmnihumanClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedAudioToVideoResponse runResponse = runClient.audioToVideo().run(
              AudioToVideoParams.builder()
                  .model(AudioToVideoModel.OMNIHUMAN_1_5)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_audio_to_video_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_audio_to_video_run_options\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      OmnihumanClient runWithOptionsClient = OmnihumanClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.audioToVideo().run(
              AudioToVideoParams.builder()
                  .model(AudioToVideoModel.OMNIHUMAN_1_5)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .prompt("A small red cube on a plain white table, studio product photo")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

    @Test
    void coversHumanidentificationResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_human_identification\",\"status\":\"processing\"}");
      OmnihumanClient createClient = OmnihumanClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.humanIdentification().create(
              HumanIdentificationParams.builder()
                  .model(HumanIdentificationModel.OMNIHUMAN_1_5_HUMAN_IDENTIFICATION)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_human_identification_options\",\"status\":\"processing\"}");
      OmnihumanClient createWithOptionsClient = OmnihumanClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.humanIdentification().create(
              HumanIdentificationParams.builder()
                  .model(HumanIdentificationModel.OMNIHUMAN_1_5_HUMAN_IDENTIFICATION)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_human_identification\",\"status\":\"completed\",\"subject_status\":1}");
      OmnihumanClient getClient = OmnihumanClient.builder().apiKey("sk-test").transport(getTransport).build();
      HumanIdentificationResponse getResponse = getClient.humanIdentification().get("task_human_identification");
      assertEquals(1, getResponse.getSubjectStatus());

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_human_identification_options\",\"status\":\"completed\",\"subject_status\":1}");
      OmnihumanClient getWithOptionsClient = OmnihumanClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.humanIdentification().get("task_human_identification_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_human_identification_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_human_identification_run\",\"status\":\"completed\",\"subject_status\":1}");
      OmnihumanClient runClient = OmnihumanClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedHumanIdentificationResponse runResponse = runClient.humanIdentification().run(
              HumanIdentificationParams.builder()
                  .model(HumanIdentificationModel.OMNIHUMAN_1_5_HUMAN_IDENTIFICATION)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertEquals(1, runResponse.getSubjectStatus());

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_human_identification_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_human_identification_run_options\",\"status\":\"completed\",\"subject_status\":1}");
      OmnihumanClient runWithOptionsClient = OmnihumanClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.humanIdentification().run(
              HumanIdentificationParams.builder()
                  .model(HumanIdentificationModel.OMNIHUMAN_1_5_HUMAN_IDENTIFICATION)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

    @Test
    void coversSubjectdetectionResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_subject_detection\",\"status\":\"processing\"}");
      OmnihumanClient createClient = OmnihumanClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.subjectDetection().create(
              SubjectDetectionParams.builder()
                  .model(SubjectDetectionModel.OMNIHUMAN_1_5_SUBJECT_DETECTION)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_subject_detection_options\",\"status\":\"processing\"}");
      OmnihumanClient createWithOptionsClient = OmnihumanClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.subjectDetection().create(
              SubjectDetectionParams.builder()
                  .model(SubjectDetectionModel.OMNIHUMAN_1_5_SUBJECT_DETECTION)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_subject_detection\",\"status\":\"completed\",\"masks\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      OmnihumanClient getClient = OmnihumanClient.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.subjectDetection().get("task_subject_detection"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_subject_detection_options\",\"status\":\"completed\",\"masks\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      OmnihumanClient getWithOptionsClient = OmnihumanClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.subjectDetection().get("task_subject_detection_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_subject_detection_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_subject_detection_run\",\"status\":\"completed\",\"masks\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      OmnihumanClient runClient = OmnihumanClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedSubjectDetectionResponse runResponse = runClient.subjectDetection().run(
              SubjectDetectionParams.builder()
                  .model(SubjectDetectionModel.OMNIHUMAN_1_5_SUBJECT_DETECTION)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_subject_detection_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_subject_detection_run_options\",\"status\":\"completed\",\"masks\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      OmnihumanClient runWithOptionsClient = OmnihumanClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.subjectDetection().run(
              SubjectDetectionParams.builder()
                  .model(SubjectDetectionModel.OMNIHUMAN_1_5_SUBJECT_DETECTION)
                  .sourceImageUrl("https://cdn.runapi.ai/public/samples/image.jpg")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

  private static JsonNode bodyJson(HttpRequest request) throws Exception {
    JsonRequestBody body = (JsonRequestBody) request.getBody();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    body.writeTo(out);
    return Json.mapper().readTree(out.toByteArray());
  }

  private static final class CapturingTransport implements HttpTransport {
    private final String body;
    private HttpRequest request;

    private CapturingTransport(String body) {
      this.body = body;
    }

    public HttpResponse send(HttpRequest request) {
      this.request = request;
      return new HttpResponse(200, body, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }

  private static final class SequenceTransport implements HttpTransport {
    private final String[] responses;
    private int calls;

    private SequenceTransport(String... responses) {
      this.responses = responses;
    }

    public HttpResponse send(HttpRequest request) {
      String response = responses[Math.min(calls, responses.length - 1)];
      calls++;
      return new HttpResponse(200, response, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }
}
