package lana.channel;

import lana.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository repository;
    private final ChannelMapper mapper;

    public Channel create(ChannelCreateDto createDto) {
        return repository.save(mapper.mapToModel(createDto));
    }

    public Channel getById(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Not found channel by id =" + id, null));
    }

    public Optional<Channel> findById(UUID id) {
        return repository.findById(id);
    }

    public Channel update(ChannelUpdateDto updateDto) {
        Channel channel = repository.getReferenceById(updateDto.getId());
        return repository.save(channel);
    }
    @Transactional
    public void deleteByTelegramId(Long id) {
        repository.deleteChannelsByTelegramId(id);
    }
    public Channel getByTelegramId(Long id) {
        return repository.findByTelegramId(id).orElseThrow(() -> new NotFoundException("Not found channel by id =" + id, null));
    }

}
