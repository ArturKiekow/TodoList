package com.arturFerreira.TodoList.service;

import com.arturFerreira.TodoList.dto.CreateTaskDto;
import com.arturFerreira.TodoList.dto.TaskResponseDto;
import com.arturFerreira.TodoList.dto.UpdateTaskDto;
import com.arturFerreira.TodoList.entity.Priority;
import com.arturFerreira.TodoList.entity.Task;
import com.arturFerreira.TodoList.exceptions.TaskAlreadyFinishedException;
import com.arturFerreira.TodoList.exceptions.TaskNotFoundException;
import com.arturFerreira.TodoList.repository.PriorityRepository;
import com.arturFerreira.TodoList.repository.TaskRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private PriorityRepository priorityRepository;

    @InjectMocks
    private TaskService taskService;

    @Captor
    private ArgumentCaptor<Task> taskArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    Priority priority;
    Task task;

    @BeforeEach
    public void setUp(){
        priority = new Priority();
        priority.setPriorityId(1L);
        priority.setName("low");
        priority.setValue(1);

        task = new Task(1L, "Teste", "teste do service", false, priority);
    }

    @Nested
    class getById {
        @Test
        @DisplayName("Deve retornar uma task com o id igual ao buscado")
        void deveRetornarUmaTaskComOIdIgualAoBuscado() {
            Long id = 1L;

            var response = TaskResponseDto.fromEntity(task);

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(task));

            var result = taskService.getById(id);
            assertEquals(response, result);
            assertEquals(id, longArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Deve retornar uma TaskNotFoundException quando não encontrar a task")
        void deveRetornarUmaTaskNotFoundExceptionQuandoNaoEncontrarATask(){

            when(taskRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(TaskNotFoundException.class, () -> taskService.getById(1L));
        }
    }

    @Nested
    class getAll {

        @Test
        @DisplayName("Deve retornar uma lista com uma task")
        void deveRetornarUmaListaDeTasks() {

            var tasks = List.of(task);

            when(taskRepository.findAll(Sort.by(Sort.Direction.DESC, "priority.value"))).thenReturn(tasks);

            var result = taskService.getAll("desc");

            assertNotNull(result);
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia")
        void deveRetornarUmaListavazia() {

            var tasks = new ArrayList<Task>();

            when(taskRepository.findAll(Sort.by(Sort.Direction.DESC, "priority.value"))).thenReturn(tasks);

            var result = taskService.getAll("desc");

            assertNotNull(result);
            assertEquals(0, result.size());
        }

    }

    @Nested
    class create {

        @Test
        @DisplayName("Deve criar uma task com sucesso")
        void deveCriarUmaTaskComSucesso() {

            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);
            when(priorityRepository.findByNameIgnoreCase(any())).thenReturn(priority);
            CreateTaskDto taskRequestDTO = new CreateTaskDto("Teste", "Descricao Teste", "low");

            var result = taskService.create(taskRequestDTO);

            assertNotNull(result);

            var taskCaptured = taskArgumentCaptor.getValue();

            assertEquals(taskRequestDTO.title(), taskCaptured.getTitle());
            assertEquals(taskRequestDTO.description(), taskCaptured.getDescription());
            assertEquals(taskRequestDTO.priority(), taskCaptured.getPriority().getName());
        }

    }

    @Nested
    class update {

        @Test
        @DisplayName("Deve atualizar o titulo, descrição e prioridade de uma task com sucesso")
        void deveAtualizarTodosOsAtributosDaTaskComSucesso() {

            UpdateTaskDto updateDto = new UpdateTaskDto("title update", "description update", "urgent");
            Priority updatePriority = new Priority();
            updatePriority.setPriorityId(4L);
            updatePriority.setName("urgent");
            updatePriority.setValue(4);

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(task));
            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);
            when(priorityRepository.findByNameIgnoreCase("urgent")).thenReturn(updatePriority);

            taskService.update(task.getId(), updateDto);

            var taskCaptured = taskArgumentCaptor.getValue();
            assertEquals(task.getId(), longArgumentCaptor.getValue());
            assertEquals(updateDto.title(), taskCaptured.getTitle());
            assertEquals(updateDto.description(), taskCaptured.getDescription());
            assertEquals(updateDto.priority(), taskCaptured.getPriority().getName());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(1))
                    .save(taskArgumentCaptor.getValue());
            verify(priorityRepository, times(1))
                    .findByNameIgnoreCase("urgent");
        }

        @Test
        @DisplayName("Deve atualizar o titulo de uma task com sucesso")
        void deveAtualizarOTituloDaTaskComSucesso() {

            UpdateTaskDto updateDto = new UpdateTaskDto("title update", null, null);

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(task));
            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);

            taskService.update(task.getId(), updateDto);

            var taskCaptured = taskArgumentCaptor.getValue();
            assertEquals(task.getId(), longArgumentCaptor.getValue());
            assertEquals(updateDto.title(), taskCaptured.getTitle());
            assertEquals(task.getDescription(), taskCaptured.getDescription());
            assertEquals(task.getPriority().getName(), taskCaptured.getPriority().getName());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(1))
                    .save(taskArgumentCaptor.getValue());
            verify(priorityRepository, times(0))
                    .findByNameIgnoreCase(any());
        }

        @Test
        @DisplayName("Deve atualizar a descrição de uma task com sucesso")
        void deveAtualizarADescricaoDaTaskComSucesso() {

            UpdateTaskDto updateDto = new UpdateTaskDto(null,  "description update", null);

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(task));
            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);

            taskService.update(task.getId(), updateDto);

            var taskCaptured = taskArgumentCaptor.getValue();
            assertEquals(task.getId(), longArgumentCaptor.getValue());
            assertEquals(task.getTitle(), taskCaptured.getTitle());
            assertEquals(updateDto.description(), taskCaptured.getDescription());
            assertEquals(task.getPriority().getName(), taskCaptured.getPriority().getName());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(1))
                    .save(taskArgumentCaptor.getValue());
            verify(priorityRepository, times(0))
                    .findByNameIgnoreCase(any());
        }

        @Test
        @DisplayName("Deve atualizar a prioridade de uma task com sucesso")
        void deveAtualizarAPrioridadeDaTaskComSucesso() {

            UpdateTaskDto updateDto = new UpdateTaskDto(null, null, "urgent");

            Priority updatePriority = new Priority();
            updatePriority.setPriorityId(4L);
            updatePriority.setName("urgent");
            updatePriority.setValue(4);

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(task));
            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);
            when(priorityRepository.findByNameIgnoreCase("urgent")).thenReturn(updatePriority);

            taskService.update(task.getId(), updateDto);

            var taskCaptured = taskArgumentCaptor.getValue();
            assertEquals(task.getId(), longArgumentCaptor.getValue());
            assertEquals(task.getTitle(), taskCaptured.getTitle());
            assertEquals(task.getDescription(), taskCaptured.getDescription());
            assertEquals(updateDto.priority(), taskCaptured.getPriority().getName());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(1))
                    .save(taskArgumentCaptor.getValue());
            verify(priorityRepository, times(1))
                    .findByNameIgnoreCase("urgent");
        }

        @Test
        @DisplayName("Deve atualizar o titulo e a descricao de uma task com sucesso")
        void deveAtualizarOTituloEADescricaoDaTaskComSucesso() {

            UpdateTaskDto updateDto = new UpdateTaskDto("title update", "description update", null);


            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(task));
            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);

            taskService.update(task.getId(), updateDto);

            var taskCaptured = taskArgumentCaptor.getValue();
            assertEquals(task.getId(), longArgumentCaptor.getValue());
            assertEquals(updateDto.title(), taskCaptured.getTitle());
            assertEquals(updateDto.description(), taskCaptured.getDescription());
            assertEquals(task.getPriority().getName(), taskCaptured.getPriority().getName());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(1))
                    .save(taskArgumentCaptor.getValue());
            verify(priorityRepository, times(0))
                    .findByNameIgnoreCase(any());
        }

        @Test
        @DisplayName("Deve atualizar o titulo e a prioridade de uma task com sucesso")
        void deveAtualizarOTituloEAPrioridadeDaTaskComSucesso() {

            UpdateTaskDto updateDto = new UpdateTaskDto("title update", null, "urgent");

            Priority updatePriority = new Priority();
            updatePriority.setPriorityId(4L);
            updatePriority.setName("urgent");
            updatePriority.setValue(4);

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(task));
            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);
            when(priorityRepository.findByNameIgnoreCase("urgent")).thenReturn(updatePriority);

            taskService.update(task.getId(), updateDto);

            var taskCaptured = taskArgumentCaptor.getValue();
            assertEquals(task.getId(), longArgumentCaptor.getValue());
            assertEquals(updateDto.title(), taskCaptured.getTitle());
            assertEquals(task.getDescription(), taskCaptured.getDescription());
            assertEquals(updateDto.priority(), taskCaptured.getPriority().getName());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(1))
                    .save(taskArgumentCaptor.getValue());
            verify(priorityRepository, times(1))
                    .findByNameIgnoreCase("urgent");
        }

        @Test
        @DisplayName("Deve atualizar a descricao e a prioridade de uma task com sucesso")
        void deveAtualizarADescricaoEAPrioridadeDaTaskComSucesso() {

            UpdateTaskDto updateDto = new UpdateTaskDto(null, "description update", "urgent");

            Priority updatePriority = new Priority();
            updatePriority.setPriorityId(4L);
            updatePriority.setName("urgent");
            updatePriority.setValue(4);

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(task));
            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(task);
            when(priorityRepository.findByNameIgnoreCase("urgent")).thenReturn(updatePriority);

            taskService.update(task.getId(), updateDto);

            var taskCaptured = taskArgumentCaptor.getValue();
            assertEquals(task.getId(), longArgumentCaptor.getValue());
            assertEquals(task.getTitle(), taskCaptured.getTitle());
            assertEquals(updateDto.description(), taskCaptured.getDescription());
            assertEquals(updateDto.priority(), taskCaptured.getPriority().getName());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(1))
                    .save(taskArgumentCaptor.getValue());
            verify(priorityRepository, times(1))
                    .findByNameIgnoreCase("urgent");
        }



        @Test
        @DisplayName("Deve retornar uma TaskNotFoundException quando não encontrar a task pelo id")
        void DeveRetornarUmaTaskNotFoundExceptionQuandoNaoEncontrarUmaTaskPeloId() {
            UpdateTaskDto updateTaskDto = new UpdateTaskDto("title update", "description update", "urgent");
            when(taskRepository.findById(any())).thenReturn(Optional.empty());

            assertThrows(TaskNotFoundException.class, () -> taskService.update(1L, updateTaskDto));
        }

        @Test
        @DisplayName("Deve retornar uma TaskAlreadyFinishedException quando a task já estiver finalizada")
        void DeveRetornarUmaTaskAlreadyFinishedExceptionQuandoATaskJaEstiverFinalizada() {

            var taskDb = new Task(1L, "Teste", "teste do service", true, priority);
            UpdateTaskDto updateTaskDto = new UpdateTaskDto("title update", "description update", "urgent");

            when(taskRepository.findById(any())).thenReturn(Optional.of(taskDb));

            assertThrows(TaskAlreadyFinishedException.class, () -> taskService.update(1L, updateTaskDto));
        }

    }

    @Nested
    class changeFinished {
        @Test
        @DisplayName("Change the atribute finished to true")
        void changeFinishedToTrue() {
            var taskNotFinished = new Task(1L, "Teste", "teste do service", false, priority);
            var taskFinished = new Task(1L, "Teste", "teste do service", true, priority);

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(taskNotFinished));
            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(taskFinished);

            var taskResponse = taskService.changeFinished(1L);

            assertEquals(task.getId(), longArgumentCaptor.getValue());
            assertTrue(taskArgumentCaptor.getValue().isFinished());
            assertTrue(taskResponse.finished());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(1))
                    .save(taskArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Change the atribute finished to false")
        void changeFinishedToFalse() {
            var taskNotFinished = new Task(1L, "Teste", "teste do service", false, priority);
            var taskFinished = new Task(1L, "Teste", "teste do service", true, priority);

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.of(taskFinished));
            when(taskRepository.save(taskArgumentCaptor.capture())).thenReturn(taskNotFinished);

            var taskResponse = taskService.changeFinished(1L);

            assertEquals(task.getId(), longArgumentCaptor.getValue());
            assertFalse(taskArgumentCaptor.getValue().isFinished());
            assertFalse(taskResponse.finished());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(1))
                    .save(taskArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Deve lancar uma TaskNotFoundException quando nao encontrar uma task Com o Id informado")
        void deveLancarUmaTaskNotFoundExceptionQuandoNaoEncontrarUmaTaskComOIdInformado (){
            Long id = 1L;

            when(taskRepository.findById(longArgumentCaptor.capture())).thenReturn(Optional.empty());

            assertThrows(TaskNotFoundException.class, () -> taskService.changeFinished(id));
            assertEquals(id, longArgumentCaptor.getValue());

            verify(taskRepository, times(1))
                    .findById(longArgumentCaptor.getValue());
            verify(taskRepository, times(0))
                    .save(any());
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Deve excluir uma task com sucesso")
        void deveExcluirUmaTaskComSucesso() {

            Long id = 1L;

            when(taskRepository.existsById(longArgumentCaptor.capture())).thenReturn(true);
            doNothing().when(taskRepository).deleteById(longArgumentCaptor.capture());

            taskService.delete(id);

            var idList = longArgumentCaptor.getAllValues();
            assertEquals(id, idList.get(0));
            assertEquals(id, idList.get(1));

            verify(taskRepository, times(1)).existsById(idList.get(0));
            verify(taskRepository, times(1)).deleteById(idList.get(1));

        }

        @Test
        @DisplayName("Deve lancar uma TaskNotFoundException quando a task nao existir")
        void deveLancarUmaTaskNotFoundExceptionQuandoATaskNaoExiste() {
            Long id = 1L;
            when(taskRepository.existsById(longArgumentCaptor.capture())).thenReturn(false);

            assertThrows(TaskNotFoundException.class, () -> taskService.delete(id));
            assertEquals(id, longArgumentCaptor.getValue());
            verify(taskRepository, times(1)).existsById(longArgumentCaptor.getValue());
            verify(taskRepository, times(0)).deleteById(any());
        }

    }
}