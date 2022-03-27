package kr.ac.seoultech;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormTest {

    @Test
    void getName() {
        String name = "j";
        Form form = new Form(new NewShape(), new NewShape(), new NewShape(), new NewShape(), name);
        System.out.println(form.getName());
        assertEquals(form.getName(),name);
    }

    @Test
    void changeForm() {
        String name = "j";
        Form form = new Form(new NewShape(), new NewShape(), new NewShape(), new NewShape(), name);

        System.out.println(form.form);
        assertEquals(form.form,1);

        form.changeForm();
        System.out.println(form.form);
        assertEquals(form.form,2);

        form.changeForm();
        System.out.println(form.form);
        assertEquals(form.form,3);

        form.changeForm();
        System.out.println(form.form);
        assertEquals(form.form,4);

        form.changeForm();
        System.out.println(form.form);
        assertEquals(form.form,1);
    }
}