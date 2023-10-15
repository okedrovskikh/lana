package lana.post;

import lana.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PostMapper mapper;

    public Post create(PostCreateDto createDto) {
        return repository.save(mapper.mapToEntity(createDto));
    }

    public Post getById(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Not found post by id =" + id, null));
    }

    public Optional<Post> findById(UUID id) {
        return repository.findById(id);
    }

    public Post update(PostUpdateDto updateDto) {
        Post post = repository.getReferenceById(updateDto.getId());
        return repository.save(post);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
