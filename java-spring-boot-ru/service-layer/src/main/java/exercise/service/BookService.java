package exercise.service;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;


    public List<BookDTO> getAll() {
        var books = repository.findAll();
        var result = books.stream()
                .map(bookMapper::map)
                .toList();
        return result;
    }

    public BookDTO findById(Long id) {
        var book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found: " + id));

        var bookDTO = bookMapper.map(book);
        return bookDTO;
    }

    public BookDTO create(BookCreateDTO bookCreateDTO) {
        var author = authorRepository.findById(bookCreateDTO.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"));

        var book = bookMapper.map(bookCreateDTO);
        repository.save(book);
        author.getBooks().add(book);

        var bookDTO = bookMapper.map(book);
        return bookDTO;
    }

    public BookDTO update(BookUpdateDTO bookUpdateDTO, Long id) {
        var book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));

        if (bookUpdateDTO.getAuthorId().isPresent()) {
            // Найти нового автора
            var newAuthor = authorRepository.findById(bookUpdateDTO.getAuthorId().get())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"));

            // Удалить книгу из старого автора (если автор изменился)
            if (!book.getAuthor().equals(newAuthor)) {
                book.getAuthor().getBooks().remove(book);
                newAuthor.getBooks().add(book);
                book.setAuthor(newAuthor);
            }
        }

        bookMapper.update(bookUpdateDTO, book);

        repository.save(book);

        return bookMapper.map(book);

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    // END
}
