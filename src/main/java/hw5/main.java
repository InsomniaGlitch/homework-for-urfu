package hw5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class spell {
    // Статы, общие для всех спеллов, отвечают за характеризацию спелла
    protected String name;
    protected int level;
    protected String distance;
    protected String duration;
    protected String description;

    public spell(String name, int level, String distance, String duration, String description) {
        this.name = name;
        this.level = level;
        this.distance = distance;
        this.duration = duration;
        this.description = description;
    }

    // Общий метод, отвечает за показ инфы
    public void showInfo() {
        System.out.println("Заклинание: " + name);
        System.out.println("Уровень: " + level);
        System.out.println("Расстояние: " + distance);
        System.out.println("Длительность: " + duration);
        System.out.println("Описание: " + description);
    }

    // Абстрактный метод, реализуется наследником и отвечает за прокаст
    public abstract void cast();
}

class Bless extends spell {
    public Bless() {
        super(
            "Bless",
            1,
            "60 футов",
            "До 1 минуты (концентрация)",
            "Добавляет 1d4 к броскам атаки и спасброскам до 3х выбранных союзников."
        );
    }

    @Override
    public void cast() {
        System.out.println("Bless: На время действия 3 выбранных союзника получают бонус 1d4.");
    }
}

class Inflict_wounds extends spell {
    public Inflict_wounds() {
        super(
            "Inflict wounds",
            1,
            "На себя",
            "Мгновенная",
            "Соверши 1 рукопашную атаку заклинанием. При попадании цель получает 3к10 некротического урона."
        );
    }

    @Override
    public void cast() {
        System.out.println("Inflict wounds: Соверши 1 рукопашную атаку заклинанием, урон: 3к10 некротикой.");
    }
}

class SpellBook implements Iterable<spell> {
    private List<spell> spells = new ArrayList<>();

    public void addSpell(spell s) {
        spells.add(s);
    }

    @Override
    public Iterator<spell> iterator() {
        return spells.iterator();
    }
}

public class main {
    public static void main(String[] args) {

        // 2. Обработать объекты через общий тип: ссылку абстрактного класса
        spell s1 = new Bless();
        spell s2 = new Inflict_wounds();

        // 3. Выбрать и реализовать ... Iterable<T> — ...
        SpellBook spellBook = new SpellBook();
        spellBook.addSpell(s1);
        spellBook.addSpell(s2);

        // 2. ... — чтобы собственную коллекцию объектов можно было обходить через for-each.
        for (spell s : spellBook) {
            s.showInfo();
            s.cast();
            System.out.println("----------------------");
        }
    }
}