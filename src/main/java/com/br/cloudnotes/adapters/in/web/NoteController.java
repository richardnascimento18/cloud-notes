package com.br.cloudnotes.adapters.in.web;

import com.br.cloudnotes.adapters.in.web.dto.request.NoteRequestDto;
import com.br.cloudnotes.adapters.in.web.dto.response.ApiResponseDto;
import com.br.cloudnotes.adapters.in.web.dto.response.NoteResponseDto;
import com.br.cloudnotes.core.model.Note;
import com.br.cloudnotes.core.ports.in.NoteUseCases;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteUseCases noteService;

    @Value("${app.version}")
    private String appVersion;

    public NoteController(NoteUseCases noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ApiResponseDto<NoteResponseDto> createNote(@Valid @RequestBody NoteRequestDto request) {
        Note note = noteService.createNote(request.userId(), request.title(), request.content());
        NoteResponseDto dto = new NoteResponseDto(note.getId(), note.getUserId(), note.getTitle(), note.getContent());

        Map<String, ApiResponseDto.Link> links = new LinkedHashMap<>();
        links.put("previous", new ApiResponseDto.Link("GET", "http://localhost:8080/notes/{userId}", "all-notes"));
        links.put("current", new ApiResponseDto.Link("POST", "http://localhost:8080/notes", "create-note"));
        links.put("next", new ApiResponseDto.Link("GET", "http://localhost:8080/notes/" + dto.getId(), "get-note"));

        return new ApiResponseDto<>(201, appVersion, dto, links);
    }
}
