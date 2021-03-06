package ua.com.bzabza.ehcs.city;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    @JsonView(City.MinimumView.class)
    public List<City> getAll(CitySpecification citySpecification) {
        return cityService.findBy(citySpecification);
    }

    @GetMapping("{cityId}")
    @JsonView(City.FullView.class)
    public City getById(@PathVariable Integer cityId) {
        return cityService.getByPk(cityId);
    }
}
