/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.Actor;
import domain.Director;
import domain.Genre;
import domain.Movie;
import domain.MovieGenre;
import domain.MoviePoster;
import domain.Production;
import domain.ProductionCompany;
import domain.Role;
import domain.User;
import domain.UserMovieCollection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Mihailo
 */
public class DbUserMovieCollectionRepository implements DbRepository<UserMovieCollection>{

    @Override
    public void insert(UserMovieCollection collection) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "INSERT INTO collection (movieID, userID) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, collection.getMovie().getMovieID());
            statement.setInt(2, collection.getUser().getUserID());
            statement.executeUpdate();
            
            statement.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to save movie.");
        }
    }

    @Override
    public void delete(UserMovieCollection umc) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "DELETE FROM collection WHERE movieID=" + umc.getMovie().getMovieID() + " AND "
                    + "userID=" + umc.getUser().getUserID();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to delete saved movie");
        }
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(UserMovieCollection obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserMovieCollection> select(String criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserMovieCollection> selectAll() throws Exception {
        try {
            List<UserMovieCollection> collection = new ArrayList<>();
            
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "SELECT * FROM collection c JOIN movie m ON (c.movieID=m.movieID) "
                    + "JOIN user u ON (u.userID=c.userID) JOIN director d ON (d.directorID=m.directorID) "
                    + "JOIN movieposter mp ON (mp.movieposterID=m.movieposterID)";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                UserMovieCollection col = new UserMovieCollection() {
                    {
                        setMovie(new Movie() {
                            {
                                setMovieID(rs.getInt("movieID"));
                                setName(rs.getString("name"));
                                setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
                                setDescription(rs.getString("description"));
                                setScore(Math.floor(rs.getDouble("score")* 100) / 100);
                                setDirector(new Director() {
                                    {
                                        setDirectorID(rs.getInt("directorID"));
                                        setFirstName(rs.getString("firstname"));
                                        setLastName(rs.getString("lastname"));
                                        setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));
                                    }
                                });
                                setMoviePoster(new MoviePoster() {
                                    {
                                        setMoviePosterID(rs.getInt("movieposterID"));
                                        setPosterImage(ImageIO.read(rs.getBlob("posterimage").getBinaryStream()));
                                    }
                                });
                            }
                        });
                        setUser(new User() {
                            {
                                setUserID(rs.getInt("userID"));
                                setUsername(rs.getString("username"));
                                setPassword(rs.getString("password"));
                                setAdmin(rs.getBoolean("admin"));
                            }
                        });
                    }
                };
                loadAssociationClasses(col.getMovie());
                collection.add(col);
            }
            
            statement.close();
            rs.close();
            
            return collection;
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Error loading movie collection!");
        }
    }
    
    private void loadAssociationClasses(Movie movie) throws Exception{
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        
        //LOAD ROLES
        String sqlRoles = "SELECT * FROM movie m JOIN role r ON (m.movieID = r.movieID) "
                + "JOIN actor a ON (r.actorID = a.actorID)"
                + "WHERE r.movieID = " + movie.getMovieID();
        Statement statementRoles = connection.createStatement();
        ResultSet rsRoles = statementRoles.executeQuery(sqlRoles);

        while(rsRoles.next()) {
            Role role = loadRole(rsRoles);

            role.setMovie(movie);
            movie.getRoles().add(role);
        }

        //LOAD MOVIE GENRES
        String sqlMovieGenres = "SELECT g.name as gname, g.genreID as ggenreID FROM movie m JOIN movie_genre mg ON (m.movieID = mg.movieID) "
                + "JOIN genre g ON (mg.genreID = g.genreID)"
                + "WHERE mg.movieID = " + movie.getMovieID();
        Statement statementMovieGenres = connection.createStatement();
        ResultSet rsMovieGenres = statementMovieGenres.executeQuery(sqlMovieGenres);

        while(rsMovieGenres.next()) {
            MovieGenre movieGenre = loadMovieGenre(rsMovieGenres);

            movieGenre.setMovie(movie);
            movie.getMovieGenres().add(movieGenre);
        }
        //LOAD PRODUCTIONS
        String sqlProductions = "SELECT pc.name as pcname, pc.pcID FROM movie m "
                + "JOIN production p ON (m.movieID = p.movieID) "
                + "JOIN productioncompany pc ON (pc.pcID = p.productioncompanyID)"
                + "WHERE p.movieID = " + movie.getMovieID();
        Statement statementProductions = connection.createStatement();
        ResultSet rsProductions = statementProductions.executeQuery(sqlProductions);

        while(rsProductions.next()) {
            Production production = loadProduction(rsProductions);

            production.setMovie(movie);
            movie.getProductions().add(production);
        }
    }
    
    private Role loadRole(ResultSet rs) throws Exception{
        Role role = new Role() {
            {
                setRoleName(rs.getString("rolename"));
                setActor(new Actor() { {
                    setActorID(rs.getInt("actorID"));
                    setFirstName(rs.getString("firstname"));
                    setLastName(rs.getString("lastname"));
                    setBiography(rs.getString("biography"));
                }});
            }  
        };
        return role;
    }
    
    private MovieGenre loadMovieGenre(ResultSet rs) throws Exception{
        MovieGenre movieGenre = new MovieGenre() {
            {
                setGenre(new Genre() { {
                    setGenreID(rs.getInt("ggenreID"));
                    setName(rs.getString("gname"));
                }});
            }  
        };
        return movieGenre;
    }
    
    private Production loadProduction(ResultSet rs) throws Exception{
        Production production = new Production() {
            {
                setProductionCompany(new ProductionCompany() { {
                    setProductionCompanyID(rs.getInt("pcID"));
                    setName(rs.getString("pcname"));
                }});
            }  
        };
        return production;
    }
}
