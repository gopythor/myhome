package com.godcoder.myhome.controller;


import com.godcoder.myhome.model.Board;
import com.godcoder.myhome.repository.BoardRepository;
import com.godcoder.myhome.service.BoardService;
import com.godcoder.myhome.validator.BoardValidator;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardValidator boardValidator;
    private final BoardService boardService;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
        @RequestParam(required = false, defaultValue = "") String searchText) {
       // Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, pageable.getPageNumber()-4);
        int endPage = Math.min(boards.getTotalPages(), pageable.getPageNumber()+4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if(id == null){
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()){
            return "board/form";
        }
        String username = authentication.getName();
        boardService.save(username, board);
        return "redirect:/board/list";
    }
}
