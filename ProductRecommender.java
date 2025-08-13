import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class ProductRecommender {

    public static void main(String[] args) {
        try {
            // 1. Load data
            DataModel model = new FileDataModel(new File("data.csv"));

            // 2. Calculate similarity between users
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // 3. Define neighborhood (N nearest users)
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // 4. Create recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // 5. Recommend items for a given user (e.g., user ID 1)
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            // 6. Display recommendations
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Recommended Item ID: " + recommendation.getItemID() +
                                   " | Preference: " + recommendation.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
