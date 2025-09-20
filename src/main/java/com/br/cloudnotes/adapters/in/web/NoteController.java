package com.br.cloudnotes.adapters.in.web;

import com.br.cloudnotes.adapters.in.web.dto.request.NoteRequestDto;
import com.br.cloudnotes.adapters.in.web.dto.response.ApiResponseDto;
import com.br.cloudnotes.adapters.in.web.dto.response.NoteResponseDto;
import com.br.cloudnotes.core.model.Note;
import com.br.cloudnotes.core.ports.in.NoteUseCases;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
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

    // Get all notes for a user
    @GetMapping("/{userId}/{page}")
    public ApiResponseDto<List<NoteResponseDto>> getAllNotes(@PathVariable("userId") String userId, @PathVariable("page") int page) throws Exception {
        List<Note> notes = noteService.getAllNotes(userId, page);
        List<NoteResponseDto> dtos = notes.stream().map(n -> new NoteResponseDto(n.getId(), n.getUserId(), n.getTitle(), n.getContent())).toList();

        int previous = page - 1;
        int next = page + 1;

        Map<String, ApiResponseDto.Link> links = new LinkedHashMap<>();
        links.put("previous", new ApiResponseDto.Link("GET", "http://localhost:8080/users/" + previous, "page-" + previous));
        links.put("current", new ApiResponseDto.Link("POST", "http://localhost:8080/users/" + page, "page-" + page));
        links.put("next", new ApiResponseDto.Link("GET", "http://localhost:8080/users/" + next, "page-" + next));

        return new ApiResponseDto<>(201, appVersion, dtos, links);
    }
}
