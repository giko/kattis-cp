package leet;

import java.util.*;

class AccountsMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        UnionFind<String> uf = new UnionFind<>();
        Map<String, String> emailToName = new HashMap<>();
        for (List<String> account : accounts) {
            Iterator<String> it = account.iterator();
            String name = it.next();
            String firstEmail = uf.makeSet(it.next());
            emailToName.put(firstEmail, name);

            while (it.hasNext()) {
                String email = it.next();
                emailToName.put(email, name);

                uf.union(firstEmail, uf.makeSet(email));
            }
        }
        Map<String, Set<String>> emailSets = new HashMap<>();
        for (String email : emailToName.keySet()) {
            emailSets.putIfAbsent(uf.find(email), new TreeSet<>());
            emailSets.get(uf.find(email)).add(email);
        }
        List<List<String>> result = new ArrayList<>();
        for (Set<String> emails : emailSets.values()) {
            List<String> account = new ArrayList<>();
            account.add(emailToName.get(emails.iterator().next()));
            account.addAll(emails);
            result.add(account);
        }
        return result;
    }
}

class UnionFind<T> {
    private Map<T, T> parent;
    private Map<T, Integer> rank;

    public UnionFind() {
        parent = new HashMap<>();
        rank = new HashMap<>();
    }

    public T makeSet(T item) {
        if (find(item) == null) {
            parent.put(item, item);
            rank.put(item, 0);
        }
        return item;
    }

    public T find(T item) {
        if (parent.get(item) == null) {
            return null;
        }
        if (!parent.get(item).equals(item)) {
            parent.put(item, find(parent.get(item)));
        }
        return parent.get(item);
    }

    public void union(T item1, T item2) {
        T root1 = find(item1);
        T root2 = find(item2);

        if (root1.equals(root2)) return;

        if (rank.get(root1) < rank.get(root2)) {
            parent.put(root1, root2);
        } else if (rank.get(root2) < rank.get(root1)) {
            parent.put(root2, root1);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank.get(root1) + 1);
        }
    }
}
