ALTER TABLE city
    ADD CONSTRAINT name_not_blank CHECK(name <> '');
ALTER TABLE city
    ADD CONSTRAINT area_min CHECK(area > 0);
ALTER TABLE city
    ADD CONSTRAINT population_min CHECK(population > 0);
ALTER TABLE city
    ADD CONSTRAINT population_density_min CHECK(population_density > 0);
ALTER TABLE city
    ADD CONSTRAINT car_code_min CHECK(car_code > 0);
ALTER TABLE city
    ADD CONSTRAINT car_code_max CHECK(car_code < 1000);
