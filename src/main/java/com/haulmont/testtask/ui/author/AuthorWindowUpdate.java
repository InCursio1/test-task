package com.haulmont.testtask.ui.author;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Author;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class AuthorWindowUpdate extends Window {
    private ListDataProvider<Author> dataProvider;
    private DaoServices<Author> services;
    private Author AuthorUpdate;

    private TextField firstNameTextField = new TextField("First name");
    private TextField middleNameTextField = new TextField("Middle name");
    private TextField lastNameTextField = new TextField("Last name");

    public AuthorWindowUpdate(ListDataProvider<Author> dataProvider,
                              DaoServices<Author> services, Author author) {
        this.dataProvider = dataProvider;
        this.services = services;
        this.AuthorUpdate = author;
        setCaption("Update");
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

        firstNameTextField.setPlaceholder("Александр");
        middleNameTextField.setPlaceholder("Сергеевич");
        lastNameTextField.setPlaceholder("Пушкин");

        mainLayout.addComponent(lastNameTextField);
        mainLayout.addComponent(firstNameTextField);
        mainLayout.addComponent(middleNameTextField);
        mainLayout.addComponents(updateButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component updateButtonWindow() {
        Button updateButton = new Button("Update");
        updateButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        updateButton.setWidthFull();

        // Validation of each form.
        Binder<Author> binder = new Binder<>();

        binder.forField(firstNameTextField)
                .withValidator(new BeanValidator(Author.class, "firstName"))
                .bind(Author::getFirstName, Author::setFirstName);

        binder.forField(middleNameTextField)
                .withValidator(new BeanValidator(Author.class, "middleName"))
                .bind(Author::getMiddleName, Author::setMiddleName);

        binder.forField(lastNameTextField)
                .withValidator(new BeanValidator(Author.class, "lastName"))
                .bind(Author::getLastName, Author::setLastName);

        binder.readBean(AuthorUpdate);

        // Сlick listener: if the data validation is successful -> update the data.
        updateButton.addClickListener(event -> {
            if (binder.isValid()) {
                try {
                    binder.writeBean(AuthorUpdate);
                    services.update(AuthorUpdate);
                    dataProvider.refreshAll();
                    close();
                    Notification.show("Success!", "", Notification.Type.HUMANIZED_MESSAGE);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            } else Notification.show("Warning: Check the date is correct!",
                    "", Notification.Type.WARNING_MESSAGE);
        });
        return updateButton;
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