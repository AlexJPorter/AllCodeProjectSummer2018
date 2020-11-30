#include "Fraction.h"
#include <algorithm>
#include <iomanip>
#include <regex>
#include <cstdlib>
#include <numeric>

using namespace std;

/* Friend functions */
Fraction operator+(int val, const Fraction &f) {
       return f + val;	
}

Fraction operator-(int val, const Fraction &f) {
	bool valP = val <= 0;
	Fraction a{val, 0, 1, valP}; 
	return a - f; 
}

Fraction operator*(int val, const Fraction &f) { 
	return f * val; 
}

Fraction::Fraction() {
	this->wholeF = 0;
	this->numeratorF = 0;
	this->denominatorF = 1;
	this->positive = 1;
}

Fraction::Fraction(int v) {
	this->wholeF = v;
	this->numeratorF = 0;
	this->denominatorF = 1;
	this->positive = positiveCheck(this->wholeF, this->numeratorF);
}

Fraction::Fraction(const Fraction &other) {
	this->numeratorF = other.numeratorF;
	this->wholeF = other.wholeF;
	this->denominatorF = other.denominatorF;
	this->positive = other.positive;
}

Fraction::Fraction(Fraction &&other) {
	this->numeratorF = other.numeratorF;
	this->wholeF = other.wholeF;
	this->denominatorF = other.denominatorF;
	this->positive = other.positive;
}

Fraction::Fraction(std::string s) {
	for(int i = 0; i < s.length(); i++) {
		if(!isdigit( s[i]) && s[i] != ' ' && s[i] != '/' && s[i] != '-') {
			throw std::invalid_argument("Contains illegal character");
		}
		//Check for multiple spaces by using flags on those characters. 
		//Check for negative denominator (if there is a slash, make sure there is a space left of it?)
	}
	//Check if there are any illegal characters here
	int space = s.find(" ");
	if(space != -1) { //There is a space
		int slash = s.find("/");
		if(slash == -1) {
			throw std::invalid_argument("Space but no slash");
		}
		else { //there's a space and a slash
			std::string wholeS = s.substr(0, space);
			std::string numeratorS = s.substr(space + 1, slash);
			std::string denominatorS = s.substr(slash + 1, s.length());

			this->wholeF = stoi(wholeS);
			this->numeratorF = stoi(numeratorS);
			this->denominatorF = stoi(denominatorS);
			this->positive = positiveCheck(wholeF, numeratorF);
			if(this->denominatorF <= 0) {
				throw std::invalid_argument("0 denoinator");
			}
			return;
		}
	}
	int slash = s.find("/");
	if(slash != -1) { //There is a slash, but no whole number
		std::string numeratorS = s.substr(0, slash);
		std::string denominatorS = s.substr(slash+1 , s.length());

		this->numeratorF = stoi(numeratorS);
		this->denominatorF = stoi(denominatorS);
		this->wholeF = 0;
		this->positive = positiveCheck(wholeF, numeratorF);
		if(this->denominatorF <= 0) {
			throw std::invalid_argument("0 denominator");
		}
		return;
	}
	//There is only the whole number
	this->wholeF = stoi(s);
	this->numeratorF = 0;
	this->denominatorF = 1;
	this->positive = positiveCheck(wholeF, numeratorF);
}

Fraction::Fraction(int w, int n, int d, bool p) {
	this->wholeF = w;
	this->numeratorF = n;
	this->denominatorF = d;
	this->positive = p;
}

int Fraction::whole() const { 
	return abs(this->wholeF); 
}

int Fraction::numerator() const { 
	return abs(this->numeratorF); 
}

int Fraction::denominator() const { 
	return abs(this->denominatorF); 
}

bool Fraction::isPositive() const { 
	//cout << positiveCheck(this->wholeF, this->numeratorF);
	return positiveCheck(this->wholeF, this->numeratorF);	
}

Fraction& Fraction::operator=(const Fraction &other) { 
	this->wholeF = other.wholeF;
	this->numeratorF = other.numeratorF;
	this->denominatorF = other.denominatorF;
	this->positive = other.positive;
	return *this; 
}

Fraction& Fraction::operator=(Fraction &&other) { 
	Fraction a = std::move(other);
	*this = a;
	return *this; 
}

Fraction Fraction::operator+(int num) const {
	int numProd = num * this->denominatorF;
	int addN = this->wholeF * this->denominatorF;
	int oldN = this->numeratorF;
	if(!(this->positive)) {
		oldN = -oldN;
	}
	int newN = addN + oldN + numProd;
	bool p = positiveCheck(0, newN);
	Fraction a{0, newN, this->denominatorF, p};
	a.reduce();
	a.makeProper();
	return a;
}

Fraction Fraction::operator+(const Fraction &other) const { 

	int productThis = this->wholeF * this->denominatorF;
	int productOther = other.wholeF * other.denominatorF;
	
	int thisN = this->numerator();
	int otherN = other.numerator();
	if(!(this->positive)) {
		thisN = -thisN;
	}
	if(!(other.positive)) {
		otherN = -otherN;
	}

	thisN = productThis + thisN;
	otherN = productOther + otherN;
	//Improper fractions thisN/this->denom and same for other
	
	int newDenom = this->denominatorF * other.denominatorF;
	thisN = thisN * other.denominatorF;
	otherN = otherN * this->denominatorF;
	//now we have common denominators thisN/newDenom and otherN/newDenom
	//we should be able to just add them up right? regardless of sign
	int newNumer = thisN + otherN;
	bool p = positiveCheck(0, newNumer);
	Fraction a{0, newNumer, newDenom, p};
	a.makeProper();
	a.reduce();
	return a;

}

Fraction Fraction::operator-() const { 
	int w = this->wholeF;
	int n = this->numeratorF;
	if(this->wholeF != 0) { //There is a whole number
		w = -w;
	}
	else { //There must be a numerator if the above isn't true
		n = -n;
	}
	bool p = positiveCheck(w, n);
	Fraction a{w, n, this->denominatorF, p};
	a.makeProper();
	a.reduce();
	return a;
}

Fraction Fraction::operator-(int val) const {
	Fraction a = *this + -val; //Work smarter not harder
	a.makeProper();
	a.reduce();
	return a; 
}	

Fraction Fraction::operator-(const Fraction &other) const { 
	Fraction a = -other;
	Fraction b = *this + a;
	b.reduce();
	b.makeProper();
	return b; 
}

Fraction Fraction::operator*(int val) const {

	int w = this->wholeF * val;
	int n = this->numeratorF * val;
	int d = this->denominatorF;
	bool p = positiveCheck(w, n);
	Fraction a{w, n, d, p};
	a.reduce();
	a.makeProper();
	return a; 
}

Fraction Fraction::operator*(const Fraction &other) const { 
	//first unproper the fraction
	int addThis = this->wholeF * this->denominatorF;
	int addOther = other.wholeF * other.denominatorF;

	int thisN = this->numerator();
	int otherN = other.numerator();

	if(!(this->positive)) {
		thisN = -thisN;
	}
	if(!(other.positive)) {
		otherN = -otherN;
	}

	thisN = thisN + addThis;
	otherN = otherN + addOther;
	//We have improper fractions thisN/this->denom and otherN/other.denom
	
	int newN = thisN * otherN;
	int newD = this->denominatorF * other.denominatorF;

	bool p = positiveCheck(0, newN);
	Fraction a{0, newN, newD, p};
	a.reduce();
	a.makeProper();
	return a;
}

optional<int> Fraction::operator[](int pos) const {	
	std::optional<int> none;
	if(pos == 0) {
		if(this->wholeF != 0) {
			return this->wholeF;
		}
		else {
			return none;
		}
	}
	else if(pos == 1) {
		if(this->numeratorF != 0) {
			return this->numeratorF;
		}
		else {
			return none;
		}
	}
	else if(pos == 2) {
		if(this->denominatorF != 1) {
			return this->denominatorF;
		}
		else {
			return none;
		}
	}
	else {
		return none; //Invalid position
	}
}

bool Fraction::operator<(const Fraction &other) const { 
	if(this->positive != other.positive) {
		return !(this->positive);
	}
	else {
		Fraction a{*this};
		Fraction b{other};
		a.makeProper();
		b.makeProper();
		if(a.wholeF != other.wholeF) 
			return a.wholeF < b.wholeF;

		int addThis = this->wholeF * this->denominatorF;
		int addOther = other.wholeF * other.denominatorF;

		int thisN = this->numerator();
		int otherN = other.numerator();
		if(!(this->positive)) {
			thisN = -thisN;
		}
		if(!(other.positive)) {
			otherN = -otherN;
		}

		thisN = addThis + thisN;
		otherN = addOther + otherN;

		thisN = thisN * other.denominatorF;
		otherN = otherN * this->denominatorF;
		
		return thisN < otherN;
	}
}

bool Fraction::operator==(const Fraction &other) const { 
	Fraction a = other.toReduced();
	Fraction b{*this};
	b.reduce();

	a.makeProper();
	b.makeProper();
	if(b.wholeF != a.wholeF)
		return false;
	if(b.numeratorF != a.numeratorF)
		return false;
	if(b.denominatorF != a.denominatorF)
	       return false;
	if(b.positive != a.positive)
		return false;
	return true;
}

void Fraction::makeProper() {
	int toAdd = this->numeratorF / this->denominatorF;
	if(this->positive) {
		this->wholeF = this->wholeF + toAdd;
		this->numeratorF = this->numeratorF % this->denominatorF;
	}
	else {
		if(this->wholeF != 0) {
			this->wholeF = this->wholeF - toAdd;
			this->numeratorF = this->numeratorF % this->denominatorF;
		}
		else {
			this->wholeF = toAdd;
			this->numeratorF = this->numeratorF % this->denominatorF;
		}
	}

	if(this->numerator() == 0) {
		this->denominatorF = 1;
	}
}

Fraction Fraction::toProper() const { 
	Fraction a{*this};
	a.makeProper();
	return a; 
}

void Fraction::reduce() {
	int n = abs(this->numeratorF);
	int d = abs(this->denominatorF);
	int GCD = std::gcd(n, d);
	this->numeratorF = this->numeratorF / GCD;
	this->denominatorF = this->denominatorF / GCD;
	//I think this is all there is. If denom is one we keep it there even
}

Fraction Fraction::toReduced() const {
        Fraction a{*this};
	a.reduce();
	return a; 
}


ostream &Fraction::writeTo(ostream &os) const { 
	Fraction a{f};
	a.makeProper();
	a.reduce();
	os << "[";
	if(a.wholeF != 0) {
		os << a.wholeF;
		if(a.numeratorF != 0) {
			os << " ";
			os << a.numeratorF;
			os << "/";
			os << a.denominatorF;
		}
	}
	else {
		if(a.numeratorF != 0) {
			os << a.numeratorF;
			os << "/";
			os << a.denominatorF;
		}
		else {
			os << "0";
		}
	}
	os << "]";
	return os;  
}

istream &Fraction::readFrom(istream &sr)  {
	bool foundStart = false;
	bool isNegative = false;
	char ch;
	while(!foundStart) {
		ch = sr.get();
		if(ch == '[') {
			foundStart = true;
			break;
		}
		if(sr.fail()) {
			throw(std::invalid_argument("No start char ["));
		}
	}
	ch = sr.get(); //check for neg
	if(ch == '-') {
		isNegative = true;
		ch = sr.get();
	}
	else if(ch == ']') {
		//Empty brackets doesn't error maybe?
	}
	else if(!(isdigit(ch))) {
		throw(std::invalid_argument("Non-numeric char found"));
	}
	//at this point, ch is the first digit of the numerator or whole
	std::string currentNumber = "";
	while(ch != ' ' && ch != '/' && ch != ']') {
		if(sr.fail()) {
			throw(std::invalid_argument("End of stream, not finished parsing"));
		}
		if(!(isdigit(ch))) {
			throw(std::invalid_argument("Non-numeric on first numeber"));
		}
		currentNumber = currentNumber + ch;
		ch = sr.get();
	}
	//now we have the first number parsed and stored in current number
	if(ch == ']' || ch == ' ') {
		this->wholeF = stoi(currentNumber);
		if(isNegative) {
			this->wholeF = -this->wholeF;
		}
		if(ch == ']') {
			this->numeratorF = 0;
			this->denominatorF = 1;
			bool p = positiveCheck(this->wholeF, 0);
			this->positive = p;
			return sr;
			//successful parse
		}
	}
	else if(ch == '/') {
		this->wholeF = 0;
		this->numeratorF = stoi(currentNumber);
		if(isNegative) {
			this->numeratorF = -this->numeratorF;
		}
	}
	ch = sr.get();
	currentNumber = "";
	//now we have ch on the second number. It could be numerator or denominator
	while(ch != '/' && ch != ']') {
		if(sr.fail()) {
			throw(std::invalid_argument("End of stream, not finished parsing"));
		}
		if(!(isdigit(ch))) {
			throw(std::invalid_argument("Non-numeric on second number"));
		}
		currentNumber = currentNumber + ch;
		ch = sr.get();
	}
	//now we have the second parsed number, either numerator or denominator
	if(ch == ']') {
		this->denominatorF = stoi(currentNumber);
		if(this->denominatorF == 0) {
			throw(std::invalid_argument("Zero denom"));
		}
		bool p = positiveCheck(0, this->denominatorF);
		this->positive = p;
		return sr;
		//successful parse of simple fraction

	}
	else if(ch == '/') {
		this->numeratorF = stoi(currentNumber);
	}
	ch = sr.get();
	currentNumber = "";
	while(ch != ']') {
		if(sr.fail()) {
			throw(std::invalid_argument("End of stream, not finished parsing"));
		}
		if(!(isdigit(ch))) {
			throw(std::invalid_argument("Non-numeric on third number"));
		}
		currentNumber = currentNumber + ch;
		ch = sr.get();
	}
	this->denominatorF = stoi(currentNumber);
	if(this->denominatorF == 0) {
		throw(std::invalid_argument("Zero denominator"));
	}
	bool p = positiveCheck(this->wholeF, this->numeratorF);
	this->positive = p;
	
	return sr;
}

bool Fraction::isReduced() const {
       	Fraction a{*this};
	a.reduce();
	if(this->numeratorF != a.numeratorF)
		return false;
	if(this->denominatorF != a.denominatorF)
		return false;
	return true;
	//make a copy of this, then reduce it
	//check if the numerators and denominators match, if they do, it's true
}

bool Fraction::isProper() const { 
	if(this->numeratorF > this->denominatorF || this->wholeF != 0)
		return false;
	return true;
}

ostream &operator<<(ostream &os, const Fraction &f) { 
	return f.writeTo(os);
}

istream &operator>>(istream &s, Fraction &f) { 
	return f.readFrom(s);
}

bool Fraction::positiveCheck(int w, int n) const{
	if(w != 0) {
		return (w >= 0);
	}
	else {
		return (n >= 0);
	}
}

#if I_DO_EXTRA_CREDIT
optional<string> Fraction::isRepeating() const { return {}; }

string Fraction::operator()(int len) const { return {}; }
#endif
