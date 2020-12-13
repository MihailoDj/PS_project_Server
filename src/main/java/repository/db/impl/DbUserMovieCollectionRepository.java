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
            
            statement.setLong(1, collection.getMovie().getMovieID());
            statement.setLong(2, collection.getUser().getUserID());
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
    public List<UserMovieCollection> select(UserMovieCollection umc) throws Exception {
        try {
            List<UserMovieCollection> collection = new ArrayList<>();
            
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "SELECT * FROM collection c JOIN movie m ON (c.movieID=m.movieID) "
                    + "JOIN user u ON (u.userID=c.userID) JOIN director d ON (d.directorID=m.directorID) "
                    + "JOIN movieposter mp ON (mp.movieposterID=m.movieposterID) WHERE c.userID=" + umc.getUser().getUserID();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                UserMovieCollection col = new UserMovieCollection();
                
                Movie movie = new Movie();
                movie.setMovieID(rs.getLong("movieID"));
                movie.setName(rs.getString("name"));
                movie.setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
                movie.setDescription(rs.getString("description"));
                movie.setScore(Math.floor(rs.getDouble("score")* 100) / 100);
                
                Director director = new Director();
                director.setDirectorID(rs.getLong("directorID"));
                director.setFirstName(rs.getString("firstname"));
                director.setLastName(rs.getString("lastname"));
                director.setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));
                
                movie.setDirector(director);
                
                MoviePoster moviePoster = new MoviePoster();
                moviePoster.setMoviePosterID(rs.getLong("movieposterID"));
                moviePoster.setPosterImage(ImageIO.read(rs.getBlob("posterimage").getBinaryStream()));
                
                movie.setMoviePoster(moviePoster);
                
                User user = new User();
                user.setUserID(rs.getLong("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
                
                col.setMovie(movie);
                col.setUser(user);
                
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
                UserMovieCollection col = new UserMovieCollection();
                
                Movie movie = new Movie();
                movie.setMovieID(rs.getLong("movieID"));
                movie.setName(rs.getString("name"));
                movie.setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
                movie.setDescription(rs.getString("description"));
                movie.setScore(Math.floor(rs.getDouble("score")* 100) / 100);
                
                Director director = new Director();
                director.setDirectorID(rs.getLong("directorID"));
                director.setFirstName(rs.getString("firstname"));
                director.setLastName(rs.getString("lastname"));
                director.setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));
                
                movie.setDirector(director);
                
                MoviePoster moviePoster = new MoviePoster();
                moviePoster.setMoviePosterID(rs.getLong("movieposterID"));
                moviePoster.setPosterImage(ImageIO.read(rs.getBlob("posterimage").getBinaryStream()));
                
                movie.setMoviePoster(moviePoster);
                
                User user = new User();
                user.setUserID(rs.getLong("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
                
                col.setMovie(movie);
                col.setUser(user);
                
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
        Actor actor = new Actor();
        actor.setActorID(rs.getLong("actorID"));
        actor.setFirstName(rs.getString("firstname"));
        actor.setLastName(rs.getString("lastname"));
        actor.setBiography(rs.getString("biography"));
                    
        Role role = new Role();
        role.setRoleName(rs.getString("rolename"));
        role.setActor(actor);
                
        return role;
    }
    
    private MovieGenre loadMovieGenre(ResultSet rs) throws Exception{
        Genre genre = new Genre();
        genre.setGenreID(rs.getLong("ggenreID"));
        genre.setName(rs.getString("gname"));
        
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setGenre(genre);
        return movieGenre;
    }
    
    private Production loadProduction(ResultSet rs) throws Exception{
        ProductionCompany pc=  new ProductionCompany();
        pc.setProductionCompanyID(rs.getLong("pcID"));
        pc.setName(rs.getString("pcname"));
        
        Production production = new Production();
        production.setProductionCompany(pc);
        
        return production;
    }
}
