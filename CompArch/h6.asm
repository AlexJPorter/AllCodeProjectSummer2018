.globl combineFour, wackySum
addi $a0, $0, 3
addi $a1, $0, 7
addi $a2, $0, 2

wackySum:
addi $s0, $a0, 0 #use s0 for i, it starts as a then increments by c
addi $s1, $a1, 0 #Use s1 for b, the conditional
addi $s2, $a2, 0 #use s2 for c, the increment
addi $t0, $0, 0 #use t0 for the sum
addi $sp, $sp, -8 #allocate two spots on stack

wackyLoop:
slt $t1, $s1, $s0 #b > i
beq $t1, 1, returnWacky #for loop no longer valid
addi $a0, $s0, 0 #a0 is i
addi $a1, $s0, 1 #a1 is i + 1 then
sra $a1, $a1, 1 #.. divided by 2
addi $a2, $s0, 2 #get a2
srl $a2, $a2, 1
addi $a3, $s0, 3 #get a3
sw $ra, 4($sp) #store ra and sum on stack
sw $t0, 0($sp)
jal combineFour #call combine
lw $t0, 0($sp) #load ra and sum back
lw $ra, 4($sp)
add $t0, $t0, $v0 #add to sum
add $s0, $s0, $s2 #increment i
j wackyLoop #return to top of loop

returnWacky:
addi $sp, $sp, 8 #deallocate stack
addi $v0, $t0, 0 #transfer sum to v0
jr $ra #return

combineFour:
addi $t0, $0, 0 #init t0
addi $t1, $0, 0
addi $t2, $0, 0

add $t0, $a0, $a1
add $t0, $t0, $a2
add $t0, $t0, $a3 #sum in t0

andi $t1, $t0, 1 #t1 is 1 if t0 is odd, otherwise 0
beqz $t1, isEven
srl $v0, $t0, 1
jr $ra
isEven:
addi $v0, $t0, 0
jr $ra

