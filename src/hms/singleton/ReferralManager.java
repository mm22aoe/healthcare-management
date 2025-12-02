package hms.singleton;

import hms.model.Referral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton responsible for managing referral queue, updates and audit.
 */
public class ReferralManager {

    private static ReferralManager instance;

    private final List<Referral> referrals;

    private ReferralManager(List<Referral> seed) {
        this.referrals = new ArrayList<>(seed);
    }

    public static void init(List<Referral> seed) {
        if (instance == null) {
            instance = new ReferralManager(seed);
        }
    }

    public static ReferralManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ReferralManager not initialised");
        }
        return instance;
    }

    public List<Referral> getReferrals() {
        return Collections.unmodifiableList(referrals);
    }

    public void addReferral(Referral r) {
        referrals.add(r);
        // Here you could simulate sending email / updating EHR
    }

    public void updateReferral(Referral updated) {
        for (int i = 0; i < referrals.size(); i++) {
            if (referrals.get(i).getReferralId().equals(updated.getReferralId())) {
                referrals.set(i, updated);
                break;
            }
        }
    }

    public void removeReferral(String referralId) {
        referrals.removeIf(r -> r.getReferralId().equals(referralId));
    }
}
