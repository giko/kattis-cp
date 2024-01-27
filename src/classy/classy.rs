use std::io::{self, BufRead, Write};
use std::collections::BTreeSet;
use std::cmp::Ordering;

fn main() {
    let stdin = io::stdin();
    let mut input = stdin.lock();
    let mut output = io::stdout();

    let t: i32 = read_line(&mut input).trim().parse().expect("Input not an integer");

    for _ in 0..t {
        let n: usize = read_line(&mut input).trim().parse().expect("Input not an integer");
        let mut persons: BTreeSet<Person> = BTreeSet::new();

        for _ in 0..n {
            let line = read_line(&mut input);
            let parts: Vec<&str> = line.trim().split_whitespace().collect();
            let person = Person::new(parts[0], parts[1]);
            persons.insert(person);
        }

        for person in &persons {
            writeln!(output, "{}", person.name).unwrap();
        }
        writeln!(output, "==============================").unwrap();
    }
}

fn read_line(input: &mut impl BufRead) -> String {
    let mut line = String::new();
    input.read_line(&mut line).expect("Failed to read line");
    line
}

#[derive(Eq)]
struct Person {
    name: String,
    classes: Vec<i32>,
}

impl Person {
    fn new(name: &str, classes: &str) -> Self {
        let name = name[..name.len() - 1].to_string();
        let classes_vec: Vec<i32> = classes.split('-')
            .rev()
            .map(|s| match s {
                "upper" => 1,
                "middle" => 0,
                "lower" => -1,
                _ => 0,
            })
            .collect();
        Person { name, classes: classes_vec }
    }
}

impl Ord for Person {
    fn cmp(&self, other: &Self) -> Ordering {
        let max_len = usize::max(self.classes.len(), other.classes.len());
        for i in 0..max_len {
            let class_self = *self.classes.get(i).unwrap_or(&0);
            let class_other = *other.classes.get(i).unwrap_or(&0);
            if class_self != class_other {
                return class_other.cmp(&class_self);
            }
        }
        self.name.to_lowercase().cmp(&other.name.to_lowercase())
    }
}

impl PartialOrd for Person {
    fn partial_cmp(&self, other: &Person) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl PartialEq for Person {
    fn eq(&self, other: &Person) -> bool {
        self.name == other.name
    }
}
