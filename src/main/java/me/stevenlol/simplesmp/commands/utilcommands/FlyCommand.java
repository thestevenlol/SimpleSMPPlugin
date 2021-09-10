package me.stevenlol.simplesmp.commands.utilcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.Claim;
import com.griefdefender.api.claim.TrustTypes;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FlyCommand extends BaseCommand {

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

    @CommandAlias("fly")
    @CommandPermission("ssmp.fly")
    public void fly(Player player) {
        if (isInOwnClaim(player) || isInTrustedClaim(player)) {
            if (player.getAllowFlight()) {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.sendMessage(Color.translate(Main.getPrefix() + "&aYou are no longer flying"));
            } else {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage(Color.translate(Main.getPrefix() + "&aYou are now flying"));
            }
        } else {
            player.sendMessage(Color.translate(Main.getPrefix() + "&cYou need to be in a claim you own or a claim that you are trusted in to be able to use that command."));
        }
    }

}
