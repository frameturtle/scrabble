public class Backtracker<C extends Configuration<C>> {
    private boolean debug;

    public Backtracker(boolean debug) {
        this.debug = debug;
        if(this.debug) {
            System.out.println("backtracker.Backtracker debugging enabled...");
        }
    }

    private void debugPrint(String msg, C config) {
        if(this.debug) {
            System.out.println(msg + "\n" + config);
        }
    }

    public C solve(C config) {
        debugPrint("Current config", config);
        if(config.isGoal()) {
            debugPrint("\tGoal config", config);
            return config;
        } else {
            for (C child : config.getSuccessors()) {
                if (child.isValid()) {
                    debugPrint("\tValid successor", child);
                    C sol = solve(child);
                    if(sol != null) {
                        return sol;
                    }
                } else {
                    debugPrint("\tInvalid successor", child);
                }
            }
            // implicit backtracking happens here
        }
        return null;
    }
}

// note: this code was made by the faculty at my University. I mean this is a private github
// so it's not like anyone but me is using this but I thought I should say it