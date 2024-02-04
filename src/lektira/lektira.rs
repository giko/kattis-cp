use std::io::{self, Read};

fn main() {
    let mut input = String::new();
    io::stdin().read_to_string(&mut input).unwrap();
    let word = input.trim_end();
    let word_chars: Vec<char> = word.chars().collect();

    let mut best_i = 0;
    let mut best_j = 1;
    for i in 0..word_chars.len() - 2 {
        for j in i + 1..word_chars.len() - 1 {
            if compare_ijs(&word_chars, i, j, best_i, best_j) < 0 {
                best_i = i;
                best_j = j;
            }
        }
    }
    let result = reverse(&word_chars, best_i, best_j);
    println!("{}", result.iter().collect::<String>());
}

fn compare_ijs(word: &[char], i1: usize, j1: usize, i2: usize, j2: usize) -> i32 {
    let li = word.len() - 1;
    let mut cp = if i1 == i2 { i1 } else { 0 };
    let rp = if j1 == j2 { j1 } else { li };
    while cp <= rp {
        let c1 = word[char_position(li, i1, j1, cp)];
        let c2 = word[char_position(li, i2, j2, cp)];
        if c1 != c2 {
            return c1 as i32 - c2 as i32;
        }
        cp += 1;
    }
    0
}

fn char_position(li: usize, i: usize, j: usize, cp: usize) -> usize {
    if i >= cp {
        i - cp
    } else if j >= cp {
        j - (cp - i - 1)
    } else {
        li - (cp - j - 1)
    }
}

fn reverse(word: &[char], i: usize, j: usize) -> Vec<char> {
    let li = word.len() - 1;
    (0..word.len())
        .map(|cp| word[char_position(li, i, j, cp)])
        .collect()
}
