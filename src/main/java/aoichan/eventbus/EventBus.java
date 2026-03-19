package aoichan.eventbus;

import java.lang.reflect.Method;
import java.util.*;

public class EventBus {

    private static final Map<Class<?>, List<ListenerMethod>> listeners = new HashMap<>();

    public static void register(Object obj) {
        for (Method m : obj.getClass().getDeclaredMethods()) {
            if (!m.isAnnotationPresent(Subscribe.class)) continue;

            Class<?> param = m.getParameterTypes()[0];
            listeners.computeIfAbsent(param, k -> new ArrayList<>())
                    .add(new ListenerMethod(obj, m));
        }
    }

    public static void post(Object event) {
        List<ListenerMethod> list = listeners.get(event.getClass());
        if (list == null) return;

        for (ListenerMethod lm : list) {
            try {
                lm.method.invoke(lm.owner, event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ListenerMethod {
        Object owner;
        Method method;

        ListenerMethod(Object o, Method m) {
            owner = o;
            method = m;
            method.setAccessible(true);
        }
    }
}
