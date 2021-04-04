package com.haulmont.testtask.ui.genre;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.dao.GenreDao;
import com.haulmont.testtask.entity.Genre;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class GenreWindowStatistic extends Window {
    private Grid<Genre> grid = new Grid<>();
    private DaoServices<Genre> services = new GenreDao();
    private ListDataProvider<Genre> dataProvider = new ListDataProvider<>(services.findAll());

    public GenreWindowStatistic() {
        setCaption("Statistic");
        setWidth("700px");
        setHeight("500px");
        setModal(true);
        setResizable(false);
        center();
        setContent(mainLayout());
    }

    private VerticalLayout mainLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.addComponent(grid);

        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
        grid.addColumn(Genre::getTitle).setCaption("Genre");
        grid.addColumn(genre -> genre.getBooks().size()).setCaption("Books");

        return layout;
    }
}
