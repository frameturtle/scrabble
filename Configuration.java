import java.util.Collection;

public interface Configuration<C extends Configuration<C>> {
    public Collection<C> getSuccessors();
    public boolean isValid();
    public boolean isGoal();
}
