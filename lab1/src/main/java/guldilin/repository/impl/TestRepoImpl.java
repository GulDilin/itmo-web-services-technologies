package guldilin.repository.impl;

import guldilin.repository.interfaces.TestRepo;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@ApplicationScoped
public class TestRepoImpl implements TestRepo {
    @Override
    public String getData() {
        return "test";
    }
}
