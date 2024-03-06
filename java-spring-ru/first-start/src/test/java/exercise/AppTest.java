package exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AppTest {

    @Test
    void testMain() {
        String result = Application.HomeController.home();
        assertThat(result.trim()).isEqualTo("Welcome to Hexlet! Hexlet");
    }
}
