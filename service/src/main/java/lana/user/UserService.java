package lana.user;

import lana.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public User create(UserCreateDto createDto) {
        return repository.save(mapper.mapToEntity(createDto));
    }

    public User getById(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Not found post by id =" + id, null));
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    public User update(UserUpdateDto updateDto) {
        User user = repository.getReferenceById(updateDto.getId());
        return repository.save(user);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
