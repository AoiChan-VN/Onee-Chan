public class AoiTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        List<String> list = new ArrayList<>();

        if (args.length == 1) {
            list.addAll(Arrays.asList("profile", "top", "break", "info"));

            if (sender.hasPermission("aoi.admin")) {
                list.addAll(Arrays.asList("setexp","addexp","setrealm","setstage","settech","reload"));
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info") ||
                args[0].startsWith("set") ||
                args[0].equalsIgnoreCase("addexp")) {

                Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
            }
        }

        if (args.length == 3) {

            if (args[0].equalsIgnoreCase("setrealm")) {
                for (Realm r : Realm.values()) list.add(r.name());
            }

            if (args[0].equalsIgnoreCase("setstage")) {
                for (Stage s : Stage.values()) list.add(s.name());
            }

            if (args[0].equalsIgnoreCase("settech")) {
                for (TechniqueTier t : TechniqueTier.values()) list.add(t.name());
            }
        }

        return list;
    }
} 
