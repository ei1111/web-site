package com.web.site.item.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/view")
public class ItemViewController {

    @GetMapping("/itemRegister")
    public String itemRegisterForm() {
        return "item/itemRegisterForm";
    }

    @GetMapping("/itemList")
    public String itemListForm() {
        return "item/itemListForm";
    }

    @GetMapping("/itemCreateForm")
    public String saveForm() {
        return "item/itemCreateForm";
    }

    @GetMapping("/{itemId}/edit")
    public String updateForm(@PathVariable Long itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "item/itemUpdateForm";
    }


    @GetMapping("{itemId}/detail")
    public String itemDetailForm(@PathVariable Long itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "item/itemDetailForm";
    }
}
