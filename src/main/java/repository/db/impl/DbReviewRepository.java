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
import domain.Review;
import domain.Role;
import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
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
public class DbReviewRepository implements DbRepository<Review>{

    @Override
    public void insert(Review review) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "INSERT INTO review (reviewID, reviewtext, reviewscore, reviewdate, movieID, userID)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            Timestamp timestamp = Timestamp.valueOf(review.getReviewDate());
            
            statement.setLong(1, review.getReviewID());
            statement.setString(2, review.getReviewText());
            statement.setInt(3, review.getReviewScore());
            statement.setTimestamp(4, timestamp);
            statement.setLong(5, review.getMovie().getMovieID());
            statement.setLong(6, review.getUser().getUserID());
            
            statement.executeUpdate();
            
            //UPDATE MOVIE WITH NEW AVERAGE SCORE
            sql = "UPDATE movie m SET score =(SELECT AVG(reviewscore) FROM review r WHERE r.movieID="
                    + review.getMovie().getMovieID() + ") WHERE m.movieID=" + review.getMovie().getMovieID();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            
            statement.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void delete(Review review) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "DELETE FROM review WHERE userID=" + review.getUser().getUserID() + 
                    " AND movieID=" + review.getMovie().getMovieID();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            
            //CHECK TO SEE IF NO REVIEWS EXIST FOR MOVIE
            sql = "SELECT * FROM review WHERE userID=" + review.getUser().getUserID() +
                    " AND movieID=" + review.getMovie().getMovieID();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            //SET TO DEFUALT IF THEY DON'T EXIST
            if(!rs.next()) {
                sql = "UPDATE movie m SET score = 0.0 WHERE m.movieID=" + review.getMovie().getMovieID();
                statement = connection.prepareStatement(sql);
                statement.executeUpdate();
            } else {
                //SET TO AVERAGE REVIEW SCORE IF THEY DO
                sql = "UPDATE movie m SET score =(SELECT AVG(reviewscore) FROM review r WHERE r.movieID="
                        + review.getMovie().getMovieID() + ") WHERE m.movieID=" + review.getMovie().getMovieID();
                statement = connection.prepareStatement(sql);
                statement.executeUpdate();
            }
            
            rs.close();
            statement.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Review review) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "UPDATE review SET reviewscore=?, reviewtext=? WHERE userID=" + review.getUser().getUserID() +
                    " AND movieID=" + review.getMovie().getMovieID();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, review.getReviewScore());
            statement.setString(2, review.getReviewText());
            statement.executeUpdate();
            
            //UPDATE AVERAGE MOVIE SCO RE
            sql = "UPDATE movie m SET score =(SELECT AVG(reviewscore) FROM review r WHERE r.movieID="
                    + review.getMovie().getMovieID() + ") WHERE m.movieID=" + review.getMovie().getMovieID();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            
            statement.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Review> select(Review r) throws Exception {
        try {
            List<Review> reviews = new ArrayList<>();
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql;
            
            if(r.getUser().getUserID() != 0l)
                    sql = "SELECT * FROM review r JOIN movie m ON (m.movieID=r.movieID) "
                    + "JOIN director d ON (m.directorID = d.directorID) "
                    + "JOIN movieposter mp ON (m.movieposterID = mp.movieposterID) "
                    + "JOIN user u ON (u.userID=r.userID) WHERE r.userID=" + r.getUser().getUserID();
            else
                sql = "SELECT * FROM review r JOIN movie m ON (m.movieID=r.movieID) "
                    + "JOIN director d ON (m.directorID = d.directorID) "
                    + "JOIN movieposter mp ON (m.movieposterID = mp.movieposterID) "
                    + "JOIN user u ON (u.userID=r.userID) WHERE r.movieID=" + r.getMovie().getMovieID();
                
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getLong("reviewID"));
                review.setReviewText(rs.getString("reviewtext"));
                review.setReviewScore(rs.getInt("reviewscore"));
                review.setReviewDate(rs.getTimestamp("reviewdate").toLocalDateTime());
                
                Movie movie = new Movie();
                movie.setMovieID(rs.getLong("movieID"));
                movie.setName(rs.getString("name"));
                movie.setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
                movie.setScore(Math.floor(rs.getDouble("score")* 100) / 100);
                movie.setDescription(rs.getString("description"));
                
                Director director = new Director();
                director.setDirectorID(rs.getLong("directorID"));
                director.setFirstName(rs.getString("firstname"));
                director.setLastName(rs.getString("lastname"));
                director.setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));
                
                MoviePoster moviePoster = new MoviePoster();
                moviePoster.setMoviePosterID(rs.getLong("movieposterID"));
                moviePoster.setPosterImage(ImageIO.read(rs.getBlob("posterimage").getBinaryStream()));
                
                movie.setDirector(director);
                movie.setMoviePoster(moviePoster);
                
                User user = new User();
                user.setUserID(rs.getLong("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
                
                review.setMovie(movie);
                review.setUser(user);
                
                loadAssociationClasses(review.getMovie());
                reviews.add(review);
            }
        
            statement.close();
            rs.close();
            return reviews;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Review> selectAll() throws Exception {
        try {
            List<Review> reviews = new ArrayList<>();
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "SELECT * FROM review r JOIN movie m ON (m.movieID=r.movieID) "
                    + "JOIN director d ON (m.directorID = d.directorID) "
                    + "JOIN movieposter mp ON (m.movieposterID = mp.movieposterID) "
                    + "JOIN user u ON (u.userID=r.userID)";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getLong("reviewID"));
                review.setReviewText(rs.getString("reviewtext"));
                review.setReviewScore(rs.getInt("reviewscore"));
                review.setReviewDate(rs.getTimestamp("reviewdate").toLocalDateTime());
                
                Movie movie = new Movie();
                movie.setMovieID(rs.getLong("movieID"));
                movie.setName(rs.getString("name"));
                movie.setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
                movie.setScore(Math.floor(rs.getDouble("score")* 100) / 100);
                movie.setDescription(rs.getString("description"));
                
                Director director = new Director();
                director.setDirectorID(rs.getLong("directorID"));
                director.setFirstName(rs.getString("firstname"));
                director.setLastName(rs.getString("lastname"));
                director.setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));
                
                MoviePoster moviePoster = new MoviePoster();
                moviePoster.setMoviePosterID(rs.getLong("movieposterID"));
                moviePoster.setPosterImage(ImageIO.read(rs.getBlob("posterimage").getBinaryStream()));
                
                movie.setDirector(director);
                movie.setMoviePoster(moviePoster);
                
                User user = new User();
                user.setUserID(rs.getLong("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
                
                review.setMovie(movie);
                review.setUser(user);
                
                loadAssociationClasses(review.getMovie());
                reviews.add(review);
            }
        
            statement.close();
            rs.close();
            return reviews;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
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
        Actor actor=  new Actor();
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
}
