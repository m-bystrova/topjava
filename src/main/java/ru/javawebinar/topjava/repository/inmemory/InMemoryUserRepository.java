package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        UsersUtil.users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("before delete {}", repository);
        User remove = repository.remove(id);

        log.info("after delete {}", repository);
        return remove != null;
    }

    @Override
    public User save(User user) {
        if(user.isNew()){
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            log.info("new save {}", user);
            return user;
        }
        User oldUser = repository.computeIfPresent(user.getId(), (id, old) -> user);

        log.info("save oldUser {}", oldUser);
        return oldUser;
    }

    @Override
    public User get(int id) {
        User user = repository.get(id);

        log.info("getById {} found user {}", id, user);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = repository.values().stream()
            .sorted(Comparator.comparing(User::getName).thenComparingInt(User::getId))
            .collect(Collectors.toList());

        log.info("getAll " + users);
        return users;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException{
        User user = repository.values().stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Not found email with " + email));

        log.info("getByEmail {}, found user {}", email, user);
        return user;
    }
}
