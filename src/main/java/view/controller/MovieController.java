/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;
import component.MovieGenreTableModel;
import component.ProductionTableModel;
import component.RoleTableModel;
import controller.Controller;
import domain.Actor;
import domain.Director;
import domain.Genre;
import domain.Movie;
import domain.MovieGenre;
import domain.MoviePoster;
import domain.Production;
import domain.ProductionCompany;
import domain.Role;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumnModel;
import util.Constants;
import util.FormMode;
import view.coordinator.ServerFormCoordinator;
import view.form.FrmMovie;

/**
 *
 * @author Mihailo
 */
public class MovieController {
    private final FrmMovie frmMovie;
    private Movie movie;

    public MovieController(FrmMovie frmMovie) {
        this.frmMovie = frmMovie;
        addActionListeners();
        addListSelectionListener();
        addKeyListener();
    }
    
    private void addKeyListener() {
        frmMovie.txtDescriptionKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                int descriptionLength = frmMovie.getTxtDescription().getText().length();
                int charsRemaining = 200 - descriptionLength;
                frmMovie.getLblCharactersRemaining().setText("Characters remaining: " + charsRemaining);
                
            }
        });
    }

    private void addActionListeners() {
        frmMovie.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblRoles(movie.getRoles());
                fillTblMovieGenres(movie.getMovieGenres());
                fillTblProduction(movie.getProductions());
            }
        });
        frmMovie.addBtnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
            
            private void add() {
                try {
                    validateForm();
                    
                    List<Role> roles = ((RoleTableModel)frmMovie.getTblRoles().getModel()).getAll();
                    List<MovieGenre> movieGenres = ((MovieGenreTableModel)frmMovie.getTblMovieGenre().getModel()).getAll();
                    List<Production> productions = ((ProductionTableModel)frmMovie.getTblProduction().getModel()).getAll();
                    
                    
                    movie.setMovieID(0l);
                    movie.setName(frmMovie.getTxtName().getText().trim());
                    movie.setDescription(frmMovie.getTxtDescription().getText().trim());
                    movie.setScore(Double.parseDouble(frmMovie.getTxtScore().getText()));
                    movie.setDirector((Director) frmMovie.getCbDirector().getSelectedItem());
                    movie.setReleaseDate(frmMovie.getReleaseDate().getDate());
                    movie.setRoles(roles);
                    movie.setMovieGenres(movieGenres);
                    movie.setProductions(productions);

                    
                    Controller.getInstance().insertMovie(movie);
                    JOptionPane.showMessageDialog(frmMovie, "Movie successfully saved!");
                    frmMovie.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(FrmMovie.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmMovie.addBtnEnableChangesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmMovie.addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmMovie.dispose();
            }
        });

        frmMovie.addBtnDeleteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                int check = JOptionPane.showConfirmDialog(frmMovie, "Are you sure?", 
                        "Delete movie", JOptionPane.YES_NO_OPTION);
                
                if (check == JOptionPane.YES_OPTION) {
                    try {
                        Controller.getInstance().deleteMovie(movie);
                        JOptionPane.showMessageDialog(frmMovie, "Movie deleted successfully!\n", "Delete movie", JOptionPane.INFORMATION_MESSAGE);
                        frmMovie.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Delete movie", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        frmMovie.addBtnUpdateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }

            private void update() {
                try {
                    validateForm();
                    int check = JOptionPane.showConfirmDialog(frmMovie, "Are you sure?", 
                        "Update movie", JOptionPane.YES_NO_OPTION);
                
                    if(check == JOptionPane.YES_OPTION){
                        
                        Movie movie = makeMovieFromForm();
                        Controller.getInstance().updateMovie(movie);
                        JOptionPane.showMessageDialog(frmMovie, "Movie updated successfully!\n", "Update movie", JOptionPane.INFORMATION_MESSAGE);
                        frmMovie.dispose();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
                
            }
        });
        frmMovie.addBtnAddActorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roleName = frmMovie.getTxtRoleName().getText().trim();
                
                if (!roleName.isEmpty()) {
                    Role role = new Role();
                    role.setMovie(movie);
                    role.setRoleName(roleName);
                    role.setActor((Actor)frmMovie.getCbActors().getSelectedItem());

                    movie.getRoles().add(role);
                    fillTblRoles(movie.getRoles());
                    frmMovie.getBtnRemoveAllRoles().setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(frmMovie, "Role name can't be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmMovie.addBtnRemoveActorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = frmMovie.getTblRoles().getSelectedRow();
                Role role = ((RoleTableModel)frmMovie.getTblRoles().getModel()).getRoleAt(selRow);
                
                movie.getRoles().remove(role);
                fillTblRoles(movie.getRoles());
                
                if (movie.getRoles().size() == 0) 
                    frmMovie.getBtnRemoveAllRoles().setEnabled(false);
            }
        });
        frmMovie.addBtnAddGenreActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MovieGenre movieGenre = new MovieGenre();
                movieGenre.setMovie(movie);
                movieGenre.setGenre((Genre)frmMovie.getCbGenres().getSelectedItem());
                
                movie.getMovieGenres().add(movieGenre);
                fillTblMovieGenres(movie.getMovieGenres());
                frmMovie.getBtnRemoveAllMovieGenres().setEnabled(true);
            }
        });
        frmMovie.addBtnRemoveGenreActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = frmMovie.getTblMovieGenre().getSelectedRow();
                MovieGenre movieGenre = ((MovieGenreTableModel)frmMovie.getTblMovieGenre().getModel()).getMovieGenreAt(selRow);
                
                movie.getMovieGenres().remove(movieGenre);
                fillTblMovieGenres(movie.getMovieGenres());
                
                if (movie.getMovieGenres().size() == 0)
                    frmMovie.getBtnRemoveAllMovieGenres().setEnabled(false);
            }
        });
        frmMovie.addBtnAddProductionCompanyActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Production productionCompany = new Production();
                productionCompany.setMovie(movie);
                productionCompany.setProductionCompany((ProductionCompany)frmMovie.getCbProductionCompanies().getSelectedItem());
                
                movie.getProductions().add(productionCompany);
                fillTblProduction(movie.getProductions());
                frmMovie.getBtnRemoveAllProductions().setEnabled(true);
            }
        });
        frmMovie.addBtnRemoveProductionCompanyActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = frmMovie.getTblProduction().getSelectedRow();
                Production productionCompany = ((ProductionTableModel)frmMovie.getTblProduction().getModel()).getProductionAt(selRow);
                
                movie.getProductions().remove(productionCompany);
                fillTblProduction(movie.getProductions());
                
                if (movie.getProductions().size() == 0)
                    frmMovie.getBtnRemoveAllProductions().setEnabled(false);
            }
        });
        frmMovie.addBtnRemoveAllRolesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(frmMovie, "Are you sure you want to delete ALL assigned roles?", 
                        "Confrim", JOptionPane.YES_NO_OPTION);
                
                if (check == JOptionPane.YES_OPTION) {
                    movie.setRoles(new ArrayList<Role>());
                    fillTblRoles(movie.getRoles());
                    frmMovie.getBtnRemoveAllRoles().setEnabled(false);
                }
            }
        });
        frmMovie.addBtnRemoveAllMovieGenresActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(frmMovie, "Are you sure you want to delete ALL assigned genres?", 
                        "Confrim", JOptionPane.YES_NO_OPTION);
                
                if (check == JOptionPane.YES_OPTION) {
                    movie.setMovieGenres(new ArrayList<MovieGenre>());
                    fillTblMovieGenres(movie.getMovieGenres());
                    frmMovie.getBtnRemoveAllMovieGenres().setEnabled(false);
                }
            }
        });
        frmMovie.addBtnRemoveAllProductionsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(frmMovie, "Are you sure you want to delete ALL assigned productions?", 
                        "Confrim", JOptionPane.YES_NO_OPTION);
                
                if (check == JOptionPane.YES_OPTION) {
                    movie.setProductions(new ArrayList<Production>());
                    fillTblProduction(movie.getProductions());
                    frmMovie.getBtnRemoveAllProductions().setEnabled(false);
                }
            }
        });
        frmMovie.addBtnUploadPosterActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
                
                chooser.setFileFilter(filter);
                chooser.setDialogTitle("Upload image");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                
                int returnValue = chooser.showOpenDialog(frmMovie);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File poster = chooser.getSelectedFile();
                    BufferedImage bi;
                    try {
                        bi = ImageIO.read(poster);
                        frmMovie.getLblPoster().setIcon(new ImageIcon(bi.getScaledInstance(frmMovie.getLblPoster().getWidth(),
                                frmMovie.getLblPoster().getHeight(), Image.SCALE_SMOOTH)));
                        
                        MoviePoster moviePoster = new MoviePoster();
                        moviePoster.setMoviePosterID(0l);
                        moviePoster.setPosterImage(bi);
                        
                        movie.setMoviePoster(moviePoster);
                        
                    } catch(IOException ex) {
                       ex.printStackTrace();
                    }
                }
            }
        });
    }
    
    private void addListSelectionListener() {
        frmMovie.getTableRolesAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmMovie.getTblRoles().getSelectedRow() != -1)
                    frmMovie.getBtnRemoveActor().setEnabled(true);
                else
                    frmMovie.getBtnRemoveActor().setEnabled(false);
            }
        });
        frmMovie.getTableMovieGenresAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmMovie.getTblMovieGenre().getSelectedRow() != -1)
                    frmMovie.getBtnRemoveGenre().setEnabled(true);
                else
                    frmMovie.getBtnRemoveGenre().setEnabled(false);
            }
        });
        frmMovie.getTableProductionAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmMovie.getTblProduction().getSelectedRow() != -1)
                    frmMovie.getBtnRemoveProductionCompany().setEnabled(true);
                else
                    frmMovie.getBtnRemoveProductionCompany().setEnabled(false);
            }
        });
    }

    public void openForm(FormMode formMode) {
        frmMovie.setTitle("Movie form");
        prepareView(formMode);
        frmMovie.setLocationRelativeTo(ServerFormCoordinator.getInstance().getMainContoller().getFrmMain());
        frmMovie.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        fillCbDirector();
        fillCbActors();
        fillCbGenres();
        fillCbProductionCompanies();
        setupComponents(formMode);
    }
    
    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                movie = new Movie();
                
                frmMovie.getTxtMovieID().setText("auto");
                frmMovie.getTxtScore().setText(String.valueOf(0));
                frmMovie.getLblCharactersRemaining().setText("Characters remaining: 200");
                
                frmMovie.getBtnCancel().setEnabled(true);
                frmMovie.getBtnDelete().setEnabled(false);
                frmMovie.getBtnUpdate().setEnabled(false);
                frmMovie.getBtnEnableChanges().setEnabled(false);
                frmMovie.getBtnAdd().setEnabled(true);
                frmMovie.getBtnUploadPoster().setEnabled(true);

                frmMovie.getTxtMovieID().setEditable(false);
                frmMovie.getTxtName().setEditable(true);
                frmMovie.getTxtDescription().setEditable(true);
                frmMovie.getTxtScore().setEditable(false);
                frmMovie.getCbDirector().setEnabled(true);
                frmMovie.getReleaseDate().setEnabled(true);
                break;
            case FORM_VIEW:
                movie = (Movie) ServerFormCoordinator.getInstance().getParam(Constants.PARAM_MOVIE);
                
                frmMovie.getLblCharactersRemaining().setText("Characters remaining: " 
                        + (200 - movie.getDescription().length()));
                
                frmMovie.getTxtMovieID().setText(movie.getMovieID() + "");
                frmMovie.getTxtName().setText(movie.getName());
                frmMovie.getTxtDescription().setText(movie.getDescription());
                frmMovie.getTxtScore().setText(String.valueOf(movie.getScore()));
                frmMovie.getReleaseDate().setDate(movie.getReleaseDate());
                frmMovie.getCbDirector().getModel().setSelectedItem(movie.getDirector());
                
                RoleTableModel rtm = new RoleTableModel(movie.getRoles());
                frmMovie.getTblRoles().setModel(rtm);
                frmMovie.getTblRoles().setEnabled(false);
                
                MovieGenreTableModel mgtm = new MovieGenreTableModel(movie.getMovieGenres());
                frmMovie.getTblMovieGenre().setModel(mgtm);
                frmMovie.getTblMovieGenre().setEnabled(false);
                
                ProductionTableModel ptm = new ProductionTableModel(movie.getProductions());
                frmMovie.getTblProduction().setModel(ptm);
                frmMovie.getTblProduction().setEnabled(false);
                
                frmMovie.getBtnCancel().setEnabled(true);
                frmMovie.getBtnDelete().setEnabled(true);
                frmMovie.getBtnUpdate().setEnabled(false);
                frmMovie.getBtnEnableChanges().setEnabled(true);
                frmMovie.getBtnAdd().setEnabled(false);
                frmMovie.getBtnUploadPoster().setEnabled(false);
                
                frmMovie.getTxtMovieID().setEditable(false);
                frmMovie.getTxtName().setEditable(false);
                frmMovie.getTxtDescription().setEditable(false);
                frmMovie.getTxtScore().setEditable(false);
                frmMovie.getCbDirector().setEnabled(false);
                frmMovie.getReleaseDate().setEnabled(false);
                
                frmMovie.getCbActors().setEnabled(false);
                frmMovie.getCbGenres().setEnabled(false);
                frmMovie.getCbProductionCompanies().setEnabled(false);
                frmMovie.getBtnAddActor().setEnabled(false);
                frmMovie.getBtnAddGenre().setEnabled(false);
                frmMovie.getBtnAddProductionCompany().setEnabled(false);
                
                frmMovie.getBtnRemoveAllRoles().setEnabled(false);
                frmMovie.getBtnRemoveAllMovieGenres().setEnabled(false);
                frmMovie.getBtnRemoveAllProductions().setEnabled(false);
                
                frmMovie.getLblPoster().setIcon(new ImageIcon(
                        movie.getMoviePoster().getPosterImage().getScaledInstance(frmMovie.getLblPoster().getWidth(),
                                frmMovie.getLblPoster().getHeight(), Image.SCALE_SMOOTH)));
                break;
            case FORM_EDIT:
                frmMovie.getBtnCancel().setEnabled(true);
                frmMovie.getBtnDelete().setEnabled(false);
                frmMovie.getBtnUpdate().setEnabled(true);
                frmMovie.getBtnEnableChanges().setEnabled(false);
                frmMovie.getBtnAdd().setEnabled(false);

                frmMovie.getTxtMovieID().setEditable(false);
                frmMovie.getTxtName().setEditable(true);
                frmMovie.getTxtDescription().setEditable(true);
                frmMovie.getTxtScore().setEditable(false);
                frmMovie.getCbDirector().setEnabled(true);
                frmMovie.getReleaseDate().setEnabled(true);
                
                frmMovie.getCbActors().setEnabled(true);
                frmMovie.getCbGenres().setEnabled(true);
                frmMovie.getCbProductionCompanies().setEnabled(true);
                frmMovie.getBtnAddActor().setEnabled(true);
                frmMovie.getBtnAddGenre().setEnabled(true);
                frmMovie.getBtnAddProductionCompany().setEnabled(true);
                
                frmMovie.getTblRoles().setEnabled(true);
                frmMovie.getTblMovieGenre().setEnabled(true);
                frmMovie.getTblProduction().setEnabled(true);
                
                frmMovie.getBtnRemoveAllRoles().setEnabled(true);
                frmMovie.getBtnRemoveAllMovieGenres().setEnabled(true);
                frmMovie.getBtnRemoveAllProductions().setEnabled(true);
                
                frmMovie.getBtnUploadPoster().setEnabled(true);
                break;
        }
    }

    private Movie makeMovieFromForm() {
        Movie movie = new Movie();
        movie.setMovieID(Long.parseLong(frmMovie.getTxtMovieID().getText().trim()));
        movie.setName(frmMovie.getTxtName().getText().trim());
        movie.setDescription(frmMovie.getTxtDescription().getText().trim());
        movie.setScore(Double.parseDouble(frmMovie.getTxtScore().getText()));
        movie.setDirector((Director) frmMovie.getCbDirector().getSelectedItem());
        movie.setReleaseDate(frmMovie.getReleaseDate().getDate());

        movie.setRoles(((RoleTableModel)frmMovie.getTblRoles().getModel()).getAll());
        movie.setMovieGenres(((MovieGenreTableModel)frmMovie.getTblMovieGenre().getModel()).getAll());
        movie.setProductions(((ProductionTableModel)frmMovie.getTblProduction().getModel()).getAll());
                
        MoviePoster moviePoster = new MoviePoster();
        Icon icon = frmMovie.getLblPoster().getIcon();
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();

        moviePoster.setMoviePosterID(0l);
        moviePoster.setPosterImage(image);
        
        movie.setMoviePoster(moviePoster);
        
        return movie;
    }
    
    public void validateForm() throws Exception{
        if (frmMovie.getTxtName().getText().trim().isEmpty() || frmMovie.getReleaseDate().getDate() == null ||
                frmMovie.getLblPoster().getIcon() == null) 
            throw new Exception("Invalid input");
        if (frmMovie.getTxtDescription().getText().length() > 200)
            throw new Exception("Limit description to 200 characters!");
    }
    
    private void fillCbDirector() {
        try {
            frmMovie.getCbDirector().removeAllItems();
            List<Director> directors = Controller.getInstance().selectAllDirectors();
            frmMovie.getCbDirector().setModel(new DefaultComboBoxModel<>(directors.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading directors", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void fillCbActors() {
        try {
            frmMovie.getCbActors().removeAllItems();
            List<Actor> actors = Controller.getInstance().selectAllActors();
            frmMovie.getCbActors().setModel(new DefaultComboBoxModel<>(actors.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading actors", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void fillCbGenres() {
        try {
            frmMovie.getCbGenres().removeAllItems();
            List<Genre> genres = Controller.getInstance().selectAllGenres();
            frmMovie.getCbGenres().setModel(new DefaultComboBoxModel<>(genres.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading genres", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void fillCbProductionCompanies() {
        try {
            frmMovie.getCbProductionCompanies().removeAllItems();
            List<ProductionCompany> productionCompanies = Controller.getInstance().selectAllProductionCompanies();
            frmMovie.getCbProductionCompanies().setModel(new DefaultComboBoxModel<>(productionCompanies.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading production companies", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void fillTblRoles(List<Role> roles) {
        RoleTableModel rtm = new RoleTableModel(roles);
        frmMovie.getTblRoles().setModel(rtm);

        setUpRoleTableColumns();
        
    }
    
    private void fillTblMovieGenres(List<MovieGenre> movieGenres) {
        MovieGenreTableModel mgtm = new MovieGenreTableModel(movieGenres);
        frmMovie.getTblMovieGenre().setModel(mgtm);

        setUpMovieGenreTableColumns();
    }
    
    private void fillTblProduction(List<Production> productionCompanies) {
        ProductionTableModel pctm = new ProductionTableModel(productionCompanies);
        frmMovie.getTblProduction().setModel(pctm);
        
        setUpProductionTableColumns();
    }
    
    private void setUpProductionTableColumns() {
        TableColumnModel tcm = frmMovie.getTblProduction().getColumnModel();
        
        frmMovie.getTblProduction().setAutoCreateRowSorter(true);
        frmMovie.getTblProduction().getTableHeader().setResizingAllowed(false);

        frmMovie.getTblProduction().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(300);
    }
    
    public void setUpRoleTableColumns(){
        TableColumnModel tcm = frmMovie.getTblRoles().getColumnModel();
        
        frmMovie.getTblRoles().setAutoCreateRowSorter(true);
        frmMovie.getTblRoles().getTableHeader().setResizingAllowed(false);

        frmMovie.getTblRoles().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
    }
    
    public void setUpMovieGenreTableColumns() {
        TableColumnModel tcm = frmMovie.getTblMovieGenre().getColumnModel();
        
        frmMovie.getTblMovieGenre().setAutoCreateRowSorter(true);
        frmMovie.getTblMovieGenre().getTableHeader().setResizingAllowed(false);

        frmMovie.getTblMovieGenre().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(300);
    }
}
