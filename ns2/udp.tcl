# Create simulator
set ns [new Simulator]

# Trace files
set tracefile [open out.tr w]
$ns trace-all $tracefile

set namfile [open out.nam w]
$ns namtrace-all $namfile

# Nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

# Links
$ns duplex-link $n0 $n1 1Mb 1ms DropTail
$ns duplex-link $n1 $n2 1Mb 1ms DropTail

# Agents
set udp [new Agent/UDP]
$ns attach-agent $n0 $udp

set null [new Agent/Null]   
$ns attach-agent $n2 $null

# Connect sender to receiver
$ns connect $udp $null

# Application
set cbr [new Application/Traffic/CBR]
$cbr set packetSize_ 50
$cbr set interval_ 0.005
$cbr attach-agent $udp

# Start/Stop
$ns at 0.5 "$cbr start"
$ns at 4.5 "$cbr stop"
$ns at 5.0 "finish"

# Finish procedure
proc finish {} {
    global ns namfile tracefile
    $ns flush-trace
    close $namfile
    close $tracefile
    exec nam out.nam &
    exit 0
}

# Run simulation
$ns run




# Run Commands
# ns udp.tcl
# nam out.nam
