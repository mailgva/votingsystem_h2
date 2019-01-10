package com.voting.web.resto;

import com.voting.model.Resto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.voting.util.Util.createErrorStrings;

@RestController
@RequestMapping("/ajax/resto")
public class RestoAjaxController extends AbstractRestoController {
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Resto> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resto get(@PathVariable("id") Resto resto) {
        return resto;
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid Resto resto, BindingResult result) {
        if (result.hasErrors()) {
            String errStrings = createErrorStrings(result);
            return new ResponseEntity<>(errStrings, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (resto.isNew()) {
            super.create(resto);
        } else {
            super.update(resto, resto.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

