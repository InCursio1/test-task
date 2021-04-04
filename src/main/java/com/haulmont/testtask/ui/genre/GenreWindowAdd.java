package com.haulmont.testtask.ui.genre;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Genre;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class GenreWindowAdd extends Window {
    private ListDataProvider<Genre> dataProvider;
    private DaoServices<Genre> services;

    private TextField titleTextField = new TextField("Title");

    public GenreWindowAdd(ListDataProvider<Genre> dataProvider, DaoServices<Genre> services) {
        this.dataProvider = dataProvider;
        this.services = services;
        setCaption("Add");
        setModal(true);
        setResizable(false);
        center();
        setContent(mainLayoutContent());
    }

    // Creates the main layout of the window.
    // Configures and adds components to the layout.
    private VerticalLayout mainLayoutContent() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidthFull();

        titleTextField.setPlaceholder("Комедия");

        mainLayout.addComponent(titleTextField);
        mainLayout.addComponents(addButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component addButtonWindow() {
        Button addButton = new Button("Ok");
        addButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        addButton.setWidthFull();

        // Validation of each form.
        Binder<Genre> binder = new Binder<>();

        binder.forField(titleTextField)
                .withValidator(new BeanValidator(Genre.class, "title"))
                .bind(Genre::getTitle, Genre::setTitle);


        // Сlick listener: if the data validation is successful -> add the data.
        addButton.addClickListener(event -> {
            if (binder.isValid()) {
                Genre addGenre = new Genre(titleTextField.getValue());
                services.save(addGenre);
                dataProvider.getItems().add(addGenre);
                dataProvider.refreshAll();
                close();
                Notification.show("Success!", "", Notification.Type.HUMANIZED_MESSAGE);
            } else {
                Notification.show("Warning: Check the date is correct!",
                        "", Notification.Type.WARNING_MESSAGE);
            }
        });
        return addButton;
    }

    // Button to cancel and close the update window.
    private Component cancelButtonWindow() {
        Button cancelButton = new Button("Cancel");
        cancelButton.addStyleNames(ValoTheme.BUTTON_DANGER);
        cancelButton.setWidthFull();

        cancelButton.addClickListener(event -> close());
        return cancelButton;
    }
}
