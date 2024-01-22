import br.ce.wcaquino.taskbackend.controller.TaskController;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
//        todo.setTask("Descricao");
        try {
            controller.save(todo);
            Assertions.fail("Não deveria chegar nesse ponto!");
        } catch (ValidationException e) {
            Assertions.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
        Task todo = new Task();
//        todo.setDueDate(LocalDate.now());
        todo.setTask("Descricao");
        try {
            controller.save(todo);
            Assertions.fail("Não deveria chegar nesse ponto!");
        } catch (ValidationException e) {
            Assertions.assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
        Task todo = new Task();
        todo.setDueDate(LocalDate.of(2010, 1, 1));
        todo.setTask("Descricao");
        try {
            controller.save(todo);
            Assertions.fail("Não deveria chegar nesse ponto!");
        } catch (ValidationException e) {
            Assertions.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        todo.setTask("Descricao");
        controller.save(todo);
        Mockito.verify(taskRepo).save(todo);
    }
}
