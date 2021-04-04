package com.haulmont.testtask.ui.genre;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Genre;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class GenreWindowUpdate extends Window {
    private ListDataProvider<Genre> dataProvider;
    private DaoServices<Genre> services;
    private Genre GenreUpdate;

    private TextField titleTextField = new TextField("Title");

    public GenreWindowUpdate(ListDataProvider<Genre> dataProvider,
                              DaoServices<Genre> services, Genre Genre) {
        this.dataProvider = dataProvider;
        this.services = services;
        this.GenreUpdate = Genre;
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

        titleTextField.setPlaceholder("Комедия");

        mainLayout.addComponent(titleTextField);
        mainLayout.addComponents(updateButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component updateButtonWindow() {
        Button updateButton = new Button("Update");
        updateButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        updateButton.setWidthFull();

        // Validation of each form.
        Binder<Genre> binder = new Binder<>();

        binder.forField(titleTextField)
                .withValidator(new BeanValidator(Genre.class, "title"))
                .bind(Genre::getTitle, Genre::setTitle);

        binder.readBean(GenreUpdate);

        // Сlick listener: if the data validation is successful -> update the data.
        updateButton.addClickListener(event -> {
            if (binder.isValid()) {
                try {
                    binder.writeBean(GenreUpdate);
                    services.update(GenreUpdate);
                    dataProvider.refreshAll();
                    close();
                    Notification.show("Success!","", Notification.Type.HUMANIZED_MESSAGE);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            } else Notification.show("Warning: Check the date is correct!",
                    "", Notification.Type.WARNING_MESSAGE );
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
