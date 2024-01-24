use std::collections::{BTreeSet, HashMap};
use std::io::{self, BufRead};

#[derive(Eq, PartialEq, Clone, Debug)]
struct Patient {
    name: String,
    level: i32,
    order: i32,
}

impl Ord for Patient {
    fn cmp(&self, other: &Self) -> std::cmp::Ordering {
        other.level.cmp(&self.level)
            .then_with(|| self.order.cmp(&other.order))
    }
}

impl PartialOrd for Patient {
    fn partial_cmp(&self, other: &Self) -> Option<std::cmp::Ordering> {
        Some(self.cmp(other))
    }
}

struct Clinic {
    patients_counter: i32,
    patients: BTreeSet<Patient>,
    name_patient_map: HashMap<String, Patient>,
}

impl Clinic {
    fn new() -> Clinic {
        Clinic {
            patients_counter: 0,
            patients: BTreeSet::new(),
            name_patient_map: HashMap::new(),
        }
    }

    fn arrive_at_clinic(&mut self, name: String, level: i32) {
        let patient = Patient {
            name: name.clone(),
            level,
            order: self.patients_counter,
        };
        self.patients.insert(patient.clone());
        self.name_patient_map.insert(name, patient);
        self.patients_counter += 1;
    }

    fn update_infection_level(&mut self, name: &str, delta: i32) {
        if let Some(patient) = self.name_patient_map.get_mut(name) {
            self.patients.remove(patient);
            patient.level += delta;
            self.patients.insert(patient.clone());
        }
    }

    fn treated(&mut self, name: &str) {
        if let Some(patient) = self.name_patient_map.remove(name) {
            self.patients.remove(&patient);
        }
    }

    fn query(&self) -> String {
        self.patients.iter().next()
            .map_or("The clinic is empty".to_string(), |p| p.name.clone())
    }
}

fn main() {
    let stdin = io::stdin();
    let mut lines = stdin.lock().lines();

    let mut clinic = Clinic::new();

    let n: i32 = lines.next().unwrap().unwrap().parse().unwrap();

    let mut count = 0;
    while let Some(line) = lines.next() {
        let line = line.unwrap();
        let parts: Vec<&str> = line.split_whitespace().collect();

        match parts[0] {
            "0" => clinic.arrive_at_clinic(parts[1].to_string(), parts[2].parse().unwrap()),
            "1" => clinic.update_infection_level(parts[1], parts[2].parse().unwrap()),
            "2" => clinic.treated(parts[1]),
            "3" => println!("{}", clinic.query()),
            _ => {}
        }

        count += 1;
        if count >= n {
            break;
        }
    }
}
