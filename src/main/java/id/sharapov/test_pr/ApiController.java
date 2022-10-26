package id.sharapov.test_pr;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
public class ApiController {

    private String[] books = new String[20];

    @GetMapping("books")
    public ResponseEntity<String> getText(@RequestParam(value = "prefix", required = false) String prefix) {
        if (prefix == null) {
            return ResponseEntity.ok(StringUtils.join(books, " ,"));
        }
        String[] books2 = new String[20];
        int index = 0;
        for (String book : books) {
            if (book == null) {
                continue;
            }
            if (book.startsWith(prefix)) {
                books2[index] = book;
                index++;
            }
        }
        return ResponseEntity.ok(StringUtils.join(books2, " ,"));
    }

    @PostMapping("books/{text}")
    public ResponseEntity<Integer> postText(@PathVariable("text") String text) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                books[i] = text;
                return ResponseEntity.ok(i);
            }
        }
        return ResponseEntity.ok(-1);
    }

    @PostMapping("books")
    public ResponseEntity<Integer> postText2(@RequestBody String text) {
        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                books[i] = text;
                return ResponseEntity.ok(i);
            }
        }
        return ResponseEntity.ok(-1);
    }

    @PutMapping("books/{id}/{text}")
    public ResponseEntity<Integer> putText(@PathVariable("id") Integer id, @PathVariable("text") String text) {
        books[id] = text;
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    @DeleteMapping("books/{id}")
    public ResponseEntity<String> putText(@PathVariable("id") Integer id) {
        if (id > 20) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Index must be less than 20");
        }
        books[id] = null;
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
