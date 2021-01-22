/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.coordinator;

import java.util.HashMap;
import java.util.Map;
import util.FormMode;
import view.controller.ActorController;
import view.controller.DirectorController;
import view.controller.MainController;
import view.controller.MovieController;
import view.controller.ViewAllActorsController;
import view.controller.ViewAllDirectorsController;
import view.controller.ViewAllMoviesController;
import view.form.FrmActor;
import view.form.FrmDirector;
import view.form.FrmMain;
import view.form.FrmMovie;
import view.form.FrmViewActors;
import view.form.FrmViewDirectors;
import view.form.FrmViewMovies;

/**
 *
 * @author Mihailo
 */
public class ServerFormCoordinator {
    private static ServerFormCoordinator instance;
    private final MainController mainController;
    private final Map<String, Object> params;
    
    private ServerFormCoordinator() {
        mainController = new MainController(new FrmMain());
        params = new HashMap<>();
    }
    
    public static ServerFormCoordinator getInstance () {
        if (instance == null)
            instance = new ServerFormCoordinator();
        return instance;
    }
    
    public void openMainForm() {
        mainController.openForm();
    }
    
    public void openMovieForm() {
        MovieController movieController = new MovieController(new FrmMovie(mainController.getFrmMain(), true));
        movieController.openForm(FormMode.FORM_ADD);
    }

    public void openViewAllMoviesForm() {
        FrmViewMovies form = new FrmViewMovies(mainController.getFrmMain(), true);
        
        ViewAllMoviesController viewAllMoviesController = new ViewAllMoviesController(form);
        viewAllMoviesController.openForm();
    }
    
    public void openMovieDetailsForm() {
        FrmMovie movieDetails = new FrmMovie(mainController.getFrmMain(), true);
        MovieController movieController = new MovieController(movieDetails);
        movieController.openForm(FormMode.FORM_VIEW);
    }
    
    public void openDirectorForm() {
        DirectorController directorControlled = new DirectorController(new FrmDirector(mainController.getFrmMain(), true));
        directorControlled.openForm(FormMode.FORM_ADD);
    }
    
    public void openViewAllDirectorsForm() {
        FrmViewDirectors form = new FrmViewDirectors(mainController.getFrmMain(), true);
        
        ViewAllDirectorsController viewAllDirectorsController = new ViewAllDirectorsController(form);
        viewAllDirectorsController.openForm();
    }
    
    public void openDirectorDetailsForm() {
        FrmDirector directorDetails = new FrmDirector(mainController.getFrmMain(), true);
        DirectorController directorController = new DirectorController(directorDetails);
        directorController.openForm(FormMode.FORM_VIEW);
    }
    
    public void openActorForm() {
        ActorController actorController = new ActorController(new FrmActor(mainController.getFrmMain(), true));
        actorController.openForm(FormMode.FORM_ADD);
    }
    
    public void openViewAllActorsForm() {
        FrmViewActors form = new FrmViewActors(mainController.getFrmMain(), true);
        
        ViewAllActorsController viewAllActorsController = new ViewAllActorsController(form);
        viewAllActorsController.openForm();
    }
    
    public void openActorDetailsForm() {
        FrmActor actorDetails = new FrmActor(mainController.getFrmMain(), true);
        ActorController actorController = new ActorController(actorDetails);
        actorController.openForm(FormMode.FORM_VIEW);
    }
    
    public MainController getMainContoller() {
        return mainController;
    }
    
    public void addParam(String name, Object key) {
        params.put(name, key);
    }

    public Object getParam(String name) {
        return params.get(name);
    }
}
