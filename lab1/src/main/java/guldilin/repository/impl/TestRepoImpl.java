package guldilin.repository.impl;

import guldilin.repository.interfaces.TestRepo;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


@NoArgsConstructor
@ApplicationScoped
public class TestRepoImpl implements TestRepo {
    @Override
    public String getData() {
        return "test";
    }
}
