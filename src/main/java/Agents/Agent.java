package Agents;

/**
 * This is an agent who will order some products
 * And based on the profile of the product and shop and his own profile
 * the agent will make a choice on how and when the delivery will proceed
 */
public class Agent {
    private AgentProfile profile;
    private GeoLocation location;

    public AgentProfile getProfile() {
        return profile;
    }

    public void setProfile(AgentProfile profile) {
        this.profile = profile;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        String s = "AgentProfile:";
        s += profile.toString();
        s+="\n";
        s += location.toString();
        return s;
    }
}
