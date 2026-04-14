package com.neuroval.notepadapi.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.neuroval.notepadapi.model.NoteDTO;
import com.neuroval.notepadapi.model.SaveResponse;
import com.neuroval.notepadapi.util.ApiConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotepadApiTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private NoteDTO noteDTO;
    private Playwright playwright;
    private APIRequestContext request;

    void createPlaywright() {
        playwright = Playwright.create();
    }

    void createAPIRequestContext(){
        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ApiConfig.BASE_URL));
    }

    @Test
    public void saveNote() throws JsonProcessingException {
        noteDTO = new NoteDTO();
        noteDTO.setTitle("Test Note Title");
        noteDTO.setContent("Test Note Content");

        APIResponse saveResponse = request.post("/v1/note/save", RequestOptions.create().setData(noteDTO));
        SaveResponse response = mapper.readValue(saveResponse.text(), SaveResponse.class);
        System.out.println(response.toString());
    }

    @BeforeAll
    void beforeAll() {
        createPlaywright();
        createAPIRequestContext();
    }

    void disposeAPIRequestContext() {
        if (request != null) {
            request.dispose();
            request = null;
        }
    }

    void closePlaywright() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    @AfterAll
    void afterAll() {
        disposeAPIRequestContext();
        closePlaywright();
    }
}
