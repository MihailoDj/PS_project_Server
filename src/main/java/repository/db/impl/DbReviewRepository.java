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
            
            statement.setInt(1, review.getReviewID());
            statement.setString(2, review.getReviewText());
            statement.setInt(3, review.getReviewScore());
            statement.setTimestamp(4, timestamp);
            statement.setInt(5, review.getMovie().getMovieID());
            statement.setInt(6, review.getUser().getUserID());
            
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
    public List<Review> select(String criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Review> selectAll() throws Exception {
        try {
            List<Review> reviews = new ArrayList<>();
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "SELECT * FROM review r JOIN movie m ON (m.movieID=r.movieID) "
                    + "JOIN director d ON (m.directorID = d.directorID) "
                    + "JOIN movieposter mp ON (m.movieposterID = mp.movieposterID)";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                Review review = new Review() {
                    {
                        setReviewID(rs.getInt("reviewID"));
                        setReviewText(rs.getString("reviewtext"));
                        setReviewScore(rs.getInt("reviewscore"));
                        setReviewDate(rs.getTimestamp("reviewdate").toLocalDateTime());
                        setMovie(new Movie(){
                            {
                                setMovieID(rs.getInt("movieID"));
                                setName(rs.getString("name"));
                                setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
                                setScore(Math.floor(rs.getDouble("score")* 100) / 100);
                                setDescription(rs.getString("description"));
                                setDirector(new Director(){
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
                                setUser(new User() {
                                {
                                    setUserID(rs.getInt("userID"));
                                    setUsername(rs.getString("username"));
                                    setPassword(rs.getString("password"));
                                    setAdmin(rs.getBoolean("admin"));
                                }
                        });
                            }
                        });
                    }  
                };
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
