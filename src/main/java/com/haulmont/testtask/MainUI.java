package com.haulmont.testtask;

import com.haulmont.testtask.ui.author.AuthorView;
import com.haulmont.testtask.ui.book.BookView;
import com.haulmont.testtask.ui.genre.GenreView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
@Title("Library")
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        setContent(mainLayout);

        TabSheet tabsheet = new TabSheet();
        mainLayout.addComponent(tabsheet);
        mainLayout.setComponentAlignment(tabsheet, Alignment.BOTTOM_CENTER);

        tabsheet.addTab(new AuthorView(), "Author");
        tabsheet.addTab(new GenreView(), "Genre");
        tabsheet.addTab(new BookView(), "Book");
        tabsheet.setSizeFull();

        Label byLoveLabel = new Label("made by love");
        byLoveLabel.addStyleNames(ValoTheme.LABEL_SMALL);
        mainLayout.addComponent(byLoveLabel);
        mainLayout.setComponentAlignment(byLoveLabel, Alignment.BOTTOM_CENTER);
    }

}