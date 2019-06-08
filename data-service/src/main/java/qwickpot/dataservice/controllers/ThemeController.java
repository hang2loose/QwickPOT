package qwickpot.dataservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qwickpot.dataservice.dtos.ThemeDto;
import qwickpot.dataservice.services.ThemeService;

@RestController
public class ThemeController {

  private ThemeService themeService;

  public ThemeController(ThemeService themeService) {
    this.themeService = themeService;
  }

  @GetMapping("/getThemeByName")
  public ThemeDto getTheme(@RequestParam(name = "ThemeName") String name) {
    return themeService.getThemeFromRepo(name);
  }
}
