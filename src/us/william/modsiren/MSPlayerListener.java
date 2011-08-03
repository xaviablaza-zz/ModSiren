package us.william.modsiren;

import com.nijiko.permissions.PermissionHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerListener;

public class MSPlayerListener extends PlayerListener
{
  public static ModSiren plugin;
  public static PermissionHandler permissionHandler;

  public MSPlayerListener(ModSiren instance)
  {
    plugin = instance;
  }

  public void onPlayerDropItem(PlayerDropItemEvent event) {
    if (event.getPlayer() != null) {
      Player player = event.getPlayer();
      player.sendMessage(ChatColor.RED + ModSiren.ILLEGALBLOCKONE_MESSAGE);
    }
    if ((event.getItemDrop().getItemStack().getType() == Material.BEDROCK) || (event.getItemDrop().getItemStack().getType() == Material.SPONGE) || (event.getItemDrop().getItemStack().getType() == Material.MOB_SPAWNER)) {
      Player player = event.getPlayer();
      if (ModSiren.permissionHandler.has(player, "Mod.Litter"))
        player.sendMessage(ChatColor.DARK_GREEN + player.getName() + ModSiren.ILLEGALBLOCKTWO_MESSAGE);
    }
  }
}