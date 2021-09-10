package me.stevenlol.simplesmp.griefdefender;

import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.Claim;
import com.griefdefender.api.claim.TrustTypes;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class DenyFlyOutsideOfClaim implements Listener {

    private boolean isFalling(Player p) {
        return p.getFallDistance() > 0;
    }

    private Claim getClaim(Location location) {
        return GriefDefender.getCore().getClaimAt(location);
    }

    private boolean isInOwnClaim(Player player) {
        Claim claim = getClaim(player.getLocation());
        if (claim == null) return false;
        if (claim.isWilderness()) return false;
        else return player.getUniqueId().equals(claim.getOwnerUniqueId());
    }

    private boolean isInTrustedClaim(Player player) {
        Claim claim = getClaim(player.getLocation());
        if (claim == null) return false;
        if (claim.isWilderness()) return false;
        return claim.isUserTrusted(player.getUniqueId(), TrustTypes.BUILDER) ||
                claim.isUserTrusted(player.getUniqueId(), TrustTypes.ACCESSOR) ||
                claim.isUserTrusted(player.getUniqueId(), TrustTypes.CONTAINER) ||
                claim.isUserTrusted(player.getUniqueId(), TrustTypes.MANAGER);
    }

    private boolean canFly(Player player) {
        Claim claim = getClaim(player.getLocation());
        if (claim == null) return false;
        if (claim.isWilderness()) return false;
        return isInOwnClaim(player) || isInTrustedClaim(player);
    }

    @EventHandler
    public void onFly(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.isFlying()) {

            if (!canFly(player)) {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setFallDistance(-player.getLocation().getBlockY());
            } else {
                player.setAllowFlight(true);
            }


        }
    }

}
