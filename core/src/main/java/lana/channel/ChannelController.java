package lana.channel;

import jakarta.validation.Valid;
import lana.models.dto.channel.ChannelCreateDto;
import lana.models.dto.channel.ChannelUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/channels")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService service;

    @PostMapping("")
    public Channel create(@RequestBody @Valid ChannelCreateDto createDto) {
        return service.create(createDto);
    }

    @GetMapping("/{id}")
    public Channel getById(@PathVariable("id") UUID id) {
        return service.getById(id);
    }

    @PutMapping("")
    public Channel update(ChannelUpdateDto updateDto) {
        return service.update(updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") UUID id) {
        service.deleteById(id);
    }
}
