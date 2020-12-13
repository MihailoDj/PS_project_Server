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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Mihailo
 */
public class DbMovieRepository implements DbRepository<Movie>{

    @Override
    public List<Movie> selectAll() throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<Movie> movies = new ArrayList<Movie>();
            
            String sql = "SELECT * FROM movie m JOIN director d ON (m.directorID = d.directorID) JOIN "
                    + "movieposter mp ON (m.movieposterID = mp.movieposterID)";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                
                Movie movie = loadMovie(rs);
                
                loadAssociationClasses(movie);
                
                movies.add(movie);
            }
            
            statement.close();
            rs.close();
            
            return movies;
        } catch (SQLException ex) {
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error loading movies!");
        }
    }

    @Override
    public void insert(Movie movie) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            
            //INSERT MOVIE POSTER
            String sql = "INSERT INTO movieposter (movieposterID, posterimage) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setLong(1, movie.getMoviePoster().getMoviePosterID());
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(movie.getMoviePoster().getPosterImage(), "jpg", baos);
            InputStream imageStream = new ByteArrayInputStream(baos.toByteArray());
            
            statement.setBlob(2, imageStream);
            statement.executeUpdate();
            
            //////////GET MOVIE POSTER ID///////////
            
            ResultSet rsKey = statement.getGeneratedKeys();
            
            if (rsKey.next()) {
                Long movieposterID = rsKey.getLong(1);
                movie.getMoviePoster().setMoviePosterID(movieposterID);
            }
            //////////////////////////////////////////
            
            //INSERT MOVIE
            sql = "INSERT INTO movie (movieID, name, releasedate, score, description, directorid, movieposterID) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setLong(1, movie.getMovieID());
            statement.setString(2, movie.getName());
            statement.setObject(3, movie.getReleaseDate(), java.sql.Types.DATE);
            statement.setDouble(4, movie.getScore());
            statement.setString(5, movie.getDescription());
            statement.setLong(6, movie.getDirector().getDirectorID());
            statement.setLong(7, movie.getMoviePoster().getMoviePosterID());
            
            statement.executeUpdate();
            rsKey = statement.getGeneratedKeys();
            
            if (rsKey.next()) {
                Long id = rsKey.getLong(1);
                movie.setMovieID(id);
                
                //INSERT ROLES
                sql = "INSERT INTO role (actorID, movieID, rolename) VALUES (?, ?, ?)";
                statement = connection.prepareStatement(sql);
                for (Role role : movie.getRoles()) {
                    statement.setLong(1, role.getActor().getActorID());
                    statement.setLong(2, movie.getMovieID());
                    statement.setString(3, role.getRoleName());
                    statement.executeUpdate();
                }
                
                //INSERT MOVIE GENRES
                sql = "INSERT INTO movie_genre (genreID, movieID) VALUES (?, ?)";
                statement = connection.prepareStatement(sql);
                for (MovieGenre movieGenre : movie.getMovieGenres()) {
                    statement.setLong(1, movieGenre.getGenre().getGenreID());
                    statement.setLong(2, movie.getMovieID());
                    statement.executeUpdate();
                }
                
                //INSERT PRODUCTIONS
                sql = "INSERT INTO production (productioncompanyID, movieID) VALUES (?, ?)";
                statement = connection.prepareStatement(sql);
                for (Production production : movie.getProductions()) {
                    statement.setLong(1, production.getProductionCompany().getProductionCompanyID());
                    statement.setLong(2, movie.getMovieID());
                    statement.executeUpdate();
                }
                
                statement.close();
                rsKey.close();
            } else {
                throw new Exception("Error inserting movie");
            }
            
            statement.close();
        } catch(SQLException e) {
            throw new Exception(e.getMessage());
        }
        
    }

    @Override
    public void delete(Movie movie) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String sql = "DELETE FROM movie WHERE movieID=" + movie.getMovieID();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        
        sql = "DELETE FROM movieposter WHERE movieposterID = " + movie.getMoviePoster().getMoviePosterID();
        statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        
        statement.close();
    }

    @Override
    public void update(Movie movie) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            
            //INSERT NEW POSTER
            String sql = "INSERT INTO movieposter (movieposterID, posterimage) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, movie.getMoviePoster().getMoviePosterID());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(movie.getMoviePoster().getPosterImage(), "jpg", baos);
            InputStream imageStream = new ByteArrayInputStream(baos.toByteArray());
            
            statement.setBlob(2, imageStream);
            statement.executeUpdate();
            ResultSet rsKey = statement.getGeneratedKeys();
            
            int movieposterID = 0;
            
            if (rsKey.next()) {
                movieposterID = rsKey.getInt(1);
            }
            
            //UPDATE BASIC MOVIE INFO
            sql = "UPDATE movie SET movieID=?, name=?, releasedate=?, score=?, description=?, directorID=?,"
                    + " movieposterID=? WHERE movieID=" + movie.getMovieID();
            statement = connection.prepareStatement(sql);
            
            statement.setLong(1, movie.getMovieID());
            statement.setString(2, movie.getName());
            statement.setObject(3, movie.getReleaseDate(), java.sql.Types.DATE);
            statement.setDouble(4, movie.getScore());
            statement.setString(5, movie.getDescription());
            statement.setLong(6, movie.getDirector().getDirectorID());
            statement.setInt(7, movieposterID);
            
            statement.executeUpdate();
            
            //REMOVE OLD POSTER
            sql = "DELETE FROM movieposter WHERE movieposterID not in (SELECT movieposterID FROM movie)";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            
            //REMOVE ROLES RELATED TO MOVIE
            sql = "DELETE FROM role WHERE movieID=" + movie.getMovieID();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            
            //INSERT ROLES
            sql = "INSERT INTO role (actorID, movieID, rolename) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            for (Role role : movie.getRoles()) {
                statement.setLong(1, role.getActor().getActorID());
                statement.setLong(2, movie.getMovieID());
                statement.setString(3, role.getRoleName());
                statement.executeUpdate();
            }
            
            //REMOVE MOVIE GENRES RELATED TO MOVIE
            sql = "DELETE FROM movie_genre WHERE movieID=" + movie.getMovieID();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();

            //INSERT MOVIE GENRES
            sql = "INSERT INTO movie_genre (genreID, movieID) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            for (MovieGenre movieGenre : movie.getMovieGenres()) {
                statement.setLong(1, movieGenre.getGenre().getGenreID());
                statement.setLong(2, movie.getMovieID());
                statement.executeUpdate();
            }
            
            //REMOVE PRODUCTIONS RELATED TO MOVIE
            sql = "DELETE FROM production WHERE movieID=" + movie.getMovieID();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();

            //INSERT PRODUCTIONS
            sql = "INSERT INTO production (productioncompanyID, movieID) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            for (Production production : movie.getProductions()) {
                statement.setLong(1, production.getProductionCompany().getProductionCompanyID());
                statement.setLong(2, movie.getMovieID());
                statement.executeUpdate();
            }
        
        } catch(Exception ex) {
            throw new Exception("Error updating movie!");
        }
    }

    @Override
    public List<Movie> select(Movie movie) throws Exception {
        try {
            List<Movie> movies = new ArrayList<>();
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "SELECT * FROM movie m JOIN director d ON (m.directorID = d.directorID)"
                    + "JOIN movieposter mp ON (m.movieposterID = mp.movieposterID) "
                    + "WHERE name like \"%" + movie.getName() + "%\" OR firstname like \"%" + movie.getDirector().getFirstName() + "%\" OR "
                    + "lastname like \"%" + movie.getDirector().getLastName() + "%\"";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                Movie m = loadMovie(rs);
                
                loadAssociationClasses(m);
                
                movies.add(m);
            }
            
            return movies;
        } catch(SQLException e) {
            throw e;
        }
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Movie loadMovie(ResultSet rs) throws Exception{
        Director director = new Director();
        director.setDirectorID(rs.getLong("directorID"));
        director.setFirstName(rs.getString("firstname"));
        director.setLastName(rs.getString("lastname"));
        director.setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));
        
        MoviePoster moviePoster = new MoviePoster();
        moviePoster.setMoviePosterID(rs.getLong("movieposterID"));
        moviePoster.setPosterImage(ImageIO.read(rs.getBlob("posterimage").getBinaryStream()));
        
        Movie movie = new Movie();
        movie.setMovieID(rs.getLong("movieID"));
        movie.setName(rs.getString("name"));
        movie.setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
        movie.setDescription(rs.getString("description"));
        movie.setScore(Math.floor(rs.getDouble("score")* 100) / 100);
        movie.setDirector(director);
        movie.setMoviePoster(moviePoster);
        
        return movie;
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
        ProductionCompany pc = new ProductionCompany();
        pc.setProductionCompanyID(rs.getLong("pcID"));
        pc.setName(rs.getString("pcname"));
        
        Production production = new Production();
        production.setProductionCompany(pc);
        
        return production;
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
}
