/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.userMovieCollection;

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
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import operation.AbstractGenericOperation;
import repository.db.DbConnectionFactory;

/**
 *
 * @author Mihailo
 */
public class SelectCollections extends AbstractGenericOperation{
    List<UserMovieCollection> collections = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.select((UserMovieCollection)param);
        while (rs.next()) {
            UserMovieCollection col = new UserMovieCollection();

            Movie movie = new Movie();
            movie.setMovieID(rs.getLong("movieID"));
            movie.setName(rs.getString("name"));
            movie.setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
            movie.setDescription(rs.getString("description"));
            movie.setScore(Math.floor(rs.getDouble("score")* 100) / 100);
            movie.setDuration(rs.getInt("duration"));

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
            user.setStatus(rs.getString("status"));

            col.setMovie(movie);
            col.setUser(user);

            loadAssociationClasses(col.getMovie());
            collections.add(col);
        }
    }

    public List<UserMovieCollection> getCollections() {
        return collections;
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
