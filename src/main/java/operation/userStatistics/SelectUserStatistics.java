/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.userStatistics;

import domain.User;
import domain.UserStatistics;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import operation.AbstractGenericOperation;

/**
 *
 * @author Mihailo
 */
public class SelectUserStatistics extends AbstractGenericOperation{
    List<UserStatistics> userStats = new ArrayList<>();
    
    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ResultSet rs = repository.selectAll((UserStatistics)param);
        
        while (rs.next()) {
            UserStatistics stats = new UserStatistics(); 

            User user = new User();
            user.setUserID(rs.getLong("userID"));
            user.setUsername(rs.getString("username"));
            user.setStatus(rs.getString("status"));
            stats.setUser(user);

            stats.setHighestRatedMovie(rs.getString("highest_rated_movie"));
            stats.setScore(rs.getDouble("score"));
            stats.setCollectionSize(rs.getInt("collection_size"));
            stats.setReviewCount(rs.getInt("review_count"));

            userStats.add(stats);
        }
    }

    public List<UserStatistics> getUserStats() {
        return userStats;
    }
    
    
}
